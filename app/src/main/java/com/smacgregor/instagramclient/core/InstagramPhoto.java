package com.smacgregor.instagramclient.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPhoto {

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

    public String getUserName() {
        return user.username;
    }

    // boiler plate classes required for GSON integration
    private class User {
        String username;
    }

    private class Likes {
        int count;
    }

    private class Caption {
        String text;
    }

    private class Images {
        private class Image {
            String url;
            int height;
        }
        @SerializedName("standard_resolution")
        Image image;
    }

    User user;
    Likes likes;
    Images images;
    Caption caption;
}
