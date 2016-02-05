package com.smacgregor.instagramclient.viewing;

import android.content.Context;
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
        @Bind(R.id.tvCaption) TextView caption;
        @Bind(R.id.ivPhoto) ImageView imageView;

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

        viewHolder.caption.setText(photo.getCaption());
        viewHolder.imageView.setImageResource(0); // clear cached data

        Picasso.with(getContext()).load(photo.getImageURL())
                .resize(viewHolder.imageWidth, 0)
                .into(viewHolder.imageView);
        return convertView;
    }
}
