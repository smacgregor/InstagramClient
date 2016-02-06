package com.smacgregor.instagramclient.core;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPost {

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

    private class Images {
        private class Image {
            String url;
            int height;
            int width;
        }
        @SerializedName("standard_resolution")
        Image image;
    }

    InstagramUser user;
    Likes likes;
    Images images;
    Caption caption;
    long createdTime;
    @SerializedName("comments")
    CommentsCollection commentsCollection;
}
