package com.smacgregor.instagramclient.core;

import com.google.gson.annotations.SerializedName;

/**
 * Created by smacgregor on 2/4/16.
 */
public class InstagramComment {
    public String getText() {
        return text;
    }

    public InstagramUser getUser() {
        return user;
    }

    @SerializedName("from")
    private InstagramUser user;
    private String text;
}
