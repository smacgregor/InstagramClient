package com.smacgregor.instagramclient.core;

import android.support.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by smacgregor on 2/2/16.
 */

public class InstagramPost {

    public static final int PHOTO = 0;
    public static final int VIDEO = 1;

    @IntDef({PHOTO, VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaType {}

    public InstagramUser getUser() {
        return user;
    }

    public String getCaption() {
        return caption != null ? caption.text : "";
    }

    public int getLikeCount() {
        return likes.count;
    }

    public String getImageURL() {
        return images.image.url;
    }

    public int getImageHeight() {
        return images.image.height;
    }

    public int getImageWidth() {
        return images.image.width;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public List<InstagramComment> getComments() {
        return commentsCollection.comments;
    }

    public @MediaType int getMediaType() {
        return mediaType == "video" ? VIDEO : PHOTO;
    }

    public InstagramVideo getVideo() {
        if (videos != null) {
            return videos.video;
        } else {
            return null;
        }
    }

    // boiler plate classes required for GSON integration

    private class Likes {
        int count;
    }

    private class Caption {
        String text;
    }

    private class CommentsCollection {
        @SerializedName("data")
        List<InstagramComment> comments;
    }

    private class Videos {
        @SerializedName("standard_resolution")
        InstagramVideo video;
    }

    private class Images {
        private class Image {
            String url;
            int height;
            int width;
        }
        @SerializedName("standard_resolution")
        Image image;
    }

    private InstagramUser user;
    private Likes likes;
    private Images images;
    private Videos videos;
    private Caption caption;
    private long createdTime;

    @SerializedName("type")
    private String mediaType;

    @SerializedName("comments")
    private CommentsCollection commentsCollection;
}
