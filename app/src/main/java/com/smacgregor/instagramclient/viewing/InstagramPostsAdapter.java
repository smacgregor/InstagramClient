package com.smacgregor.instagramclient.viewing;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smacgregor.instagramclient.R;
import com.smacgregor.instagramclient.core.InstagramComment;
import com.smacgregor.instagramclient.core.InstagramPost;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPostsAdapter extends ArrayAdapter<InstagramPost> {

    // implement the ViewHolder pattern
    static class ViewHolder {
        @Bind(R.id.tvCaption)
        TextView caption;

        @Bind(R.id.ivPhoto)
        ImageView imageView;

        @Bind(R.id.profilePicture)
        ImageView profilePicture;

        @Bind(R.id.userName)
        TextView userNameTextView;

        @Bind(R.id.likesCount)
        TextView likesCountTextView;

        @Bind(R.id.timeStamp)
        TextView timeStampTextView;

        @Bind(R.id.likeHeart)
        ImageView likeHeartImageView;

        @Bind(R.id.tvFirstComment)
        TextView firstComment;

        @Bind(R.id.tvSecondComment)
        TextView secondComment;

        int imageWidth;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public InstagramPostsAdapter(Context context, List<InstagramPost> instagramPhotos) {
        super(context, android.R.layout.simple_list_item_1, instagramPhotos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPost post = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.imageWidth = DeviceDimensionsHelper.getDisplayWidth(getContext()); // avoid re-calculating
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            prepareViewForReUse(viewHolder);
        }

        viewHolder.userNameTextView.setText(post.getUser().getUserName());
        setupTimeStamp(post.getCreatedTime(), viewHolder.timeStampTextView);
        setupLikesCount(post.getLikeCount(), viewHolder.likesCountTextView);
        setupCommentsForPost(post.getComments(), viewHolder);
        setCommentText(post.getUser().getUserName(), post.getCaption(), viewHolder.caption);

        // To Do - can't this be sized correctly in the XML without using Picasso
        // to resize this drawable?
        Picasso.with(getContext()).load(R.drawable.small_like_heart)
                .resize(20, 0)
                .into(viewHolder.likeHeartImageView);

        Picasso.with(getContext()).load(post.getImageURL())
                .placeholder(R.drawable.photo_placeholder)
                .resize(viewHolder.imageWidth, 0)
                .into(viewHolder.imageView);

        Picasso.with(getContext()).load(post.getUser().profilePicture())
                .resize(50, 50)
                .into(viewHolder.profilePicture);

        return convertView;
    }

    /**
     * Prepare a cached view for reuse by clearing out appropriate fields
     * @param viewHolder
     */
    private void prepareViewForReUse(ViewHolder viewHolder) {
        viewHolder.imageView.setImageResource(0); // clear cached data
        viewHolder.secondComment.setText("");
        viewHolder.firstComment.setText("");
        viewHolder.caption.setText("");
    }

    private void setCommentText(final String userName, final String comment, TextView textView) {
        // To Do - make this smarter - tokenizing all words beginning with '@' or '#'
        int pos = comment.indexOf("#");
        String htmlCaption = getContext().
                getResources().
                getString(R.string.htmlFormattedComment,
                        userName,
                        (pos > 0) ? comment.substring(0, pos) : comment,
                        (pos > 0) ? comment.substring(pos) : "");
        textView.setText(Html.fromHtml(htmlCaption));
    }

    private void setupLikesCount(final int numberOfLikes, TextView likesCountTextView) {
        String numberOfLikesString = getContext().getResources().getQuantityString(R.plurals.numberOfLikes, numberOfLikes, numberOfLikes);
        likesCountTextView.setText(numberOfLikesString);
    }

    private void setupCommentsForPost(final List<InstagramComment> comments, final ViewHolder viewHolder) {
        if (comments.size() > 1) {
            setCommentText(comments.get(comments.size() - 1).getUser().getUserName(), comments.get(comments.size() - 1).getText(), viewHolder.secondComment);
        }

        if (comments.size() > 2) {
            setCommentText(comments.get(comments.size() - 2).getUser().getUserName(), comments.get(comments.size() - 2).getText(), viewHolder.firstComment);
        }
    }

    private void setupTimeStamp(final long postCreatedTime, final TextView timeStampTextView) {
        // convert the time stamp into a short relative date like 4h or 2 days
        CharSequence formattedDate = DateUtils.getRelativeTimeSpanString(postCreatedTime* DateUtils.SECOND_IN_MILLIS,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        timeStampTextView.setText(formattedDate);
    }
}
