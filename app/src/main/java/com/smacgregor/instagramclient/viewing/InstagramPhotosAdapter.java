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
import com.smacgregor.instagramclient.core.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

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

        int imageWidth;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> instagramPhotos) {
        super(context, android.R.layout.simple_list_item_1, instagramPhotos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.imageWidth = DeviceDimensionsHelper.getDisplayWidth(getContext()); // avoid re-calculating
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        String caption = photo.getCaption();
        int pos = caption.indexOf("#");
        String htmlCaption = getContext().
                getResources().
                getString(R.string.htmlFormattedComment,
                        photo.getUser().getUserName(),
                        (pos > 0) ? caption.substring(0, pos) : caption,
                        (pos > 0) ? caption.substring(pos) : "");
        viewHolder.caption.setText(Html.fromHtml(htmlCaption));

        viewHolder.userNameTextView.setText(photo.getUser().getUserName());
        viewHolder.imageView.setImageResource(0); // clear cached data
        CharSequence formattedDate = DateUtils.getRelativeTimeSpanString(photo.getCreatedTime() * DateUtils.SECOND_IN_MILLIS,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        viewHolder.timeStampTextView.setText(formattedDate);
        int numberOfLikes = photo.getLikeCount();
        String numberOfLikesString = getContext().getResources().getQuantityString(R.plurals.numberOfLikes, numberOfLikes, numberOfLikes);
        viewHolder.likesCountTextView.setText(numberOfLikesString);

        Picasso.with(getContext()).load(R.drawable.small_like_heart)
                .resize(20, 0)
                .into(viewHolder.likeHeartImageView);

        Picasso.with(getContext()).load(photo.getImageURL())
                .placeholder(R.drawable.photo_placeholder)
                .resize(viewHolder.imageWidth, 0)
                .into(viewHolder.imageView);

        Picasso.with(getContext()).load(photo.getUser().profilePicture())
                .resize(50, 50)
                .into(viewHolder.profilePicture);

        return convertView;
    }
}
