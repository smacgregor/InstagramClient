package com.smacgregor.instagramclient.viewing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.smacgregor.instagramclient.R;
import com.smacgregor.instagramclient.core.InstagramPost;
import com.smacgregor.instagramclient.core.InstagramPostsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final String INSTAGRAM_CLIENTID = "e05c462ebd86446ea48a5af73769b602";

    private List<InstagramPost> posts;
    private InstagramPostsAdapter postsAdapter;

    @Bind(R.id.listViewPosts) ListView listViewPosts;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        posts = new ArrayList<>();
        postsAdapter = new InstagramPostsAdapter(this, posts);
        listViewPosts.setAdapter(postsAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setNestedScrollingEnabled(true);

        // send out an api request to popular posts
        // On cold launch our refresh animation wont' fire unless it's done
        // outside of onCreate...
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fetchPopularPosts();
            }
        });

    }

    public void fetchPopularPosts() {
        swipeRefreshLayout.setRefreshing(true);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.instagram.com/v1/media/popular/?client_id=" + INSTAGRAM_CLIENTID, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.i("DEBUG", response.toString());
                posts.clear();
                posts.addAll(InstagramPostsResponse.parseJSON(response).posts);
                postsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Do something useful
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchPopularPosts();
    }

    @OnItemClick(R.id.listViewPosts)
    public void onLoadVideo(int position) {
        InstagramPost post = postsAdapter.getItem(position);
        if (post.getVideo() != null) {
            Intent intent = new Intent(PhotosActivity.this, FullScreenVideoActivity.class);
            intent.putExtra("video_url", post.getVideo().getUrl());
            startActivity(intent);
        }
    }
}
