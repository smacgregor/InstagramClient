package com.smacgregor.instagramclient.viewing;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
