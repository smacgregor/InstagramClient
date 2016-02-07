package com.smacgregor.instagramclient.core;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by smacgregor on 2/2/16.
 * The model for an instagram post. A post is composes a user (the poster),
 * an image, a video (optional), comments, caption and likes.
 */

public class InstagramPost {

    /**
     * Returns the user that created the post
     * @return
     */
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

    public long getCreatedTime() {
        return createdTime;
    }

    public List<InstagramComment> getComments() {
        return commentsCollection.comments;
    }

    public InstagramVideo getVideo() {
        if (videos != null) {
            return videos.video;
        } else {
            return null;
        }
    }

    // boiler plate classes required for GSON to
    // auto create our model objects

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

    @SerializedName("comments")
    private CommentsCollection commentsCollection;
}
