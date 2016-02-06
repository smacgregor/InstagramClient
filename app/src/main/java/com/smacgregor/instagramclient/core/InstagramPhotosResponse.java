package com.smacgregor.instagramclient.core;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacgregor on 2/2/16.
 */
public class InstagramPhotosResponse {
    @SerializedName("data")
    public List<InstagramPhoto> photos;

    public InstagramPhotosResponse() {
        photos = new ArrayList<>();
    }

    public static InstagramPhotosResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        InstagramPhotosResponse instagramPhotosResponse = gson.fromJson(response, InstagramPhotosResponse.class);
        return instagramPhotosResponse;
    }
}
