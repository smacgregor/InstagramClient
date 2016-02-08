package com.smacgregor.instagramclient.core;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Deserialize a JSON response from instagram into a list of InstagramPosts
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPostsResponse {
    @SerializedName("data") public List<InstagramPost> posts;

    public InstagramPostsResponse() {
        posts = new ArrayList<>();
    }

    public static InstagramPostsResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        InstagramPostsResponse instagramPostsResponse = gson.fromJson(response, InstagramPostsResponse.class);
        return instagramPostsResponse;
    }
}
