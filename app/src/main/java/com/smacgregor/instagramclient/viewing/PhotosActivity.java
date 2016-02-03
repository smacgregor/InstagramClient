package com.smacgregor.instagramclient.viewing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smacgregor.instagramclient.R;
import com.smacgregor.instagramclient.core.InstagramPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    private final String INSTAGRAM_CLIENTID = "e05c462ebd86446ea48a5af73769b602";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter photosAdapter;

    @Bind(R.id.listViewPhotos) ListView listViewPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        ButterKnife.bind(this);

        photos = new ArrayList<>();
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        listViewPhotos.setAdapter(photosAdapter);

        // send out an api request to popular photos
        fetchPopularPhotos();
    }

    public void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("key", "value");
        params.put("more", "data");
        client.get("https://api.instagram.com/v1/media/popular/?client_id=" + INSTAGRAM_CLIENTID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                JSONArray photosJson = null;
                try {
                    photosJson = response.getJSONArray("data"); // array of posts
                    for (int index = 0; index < photosJson.length(); index++) {
                        JSONObject photoJson = photosJson.getJSONObject(index);
                        // decode our photo attributes into our data model
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJson.getJSONObject("user").getString("username");
                        photo.caption = photoJson.getJSONObject("caption").getString("text");
                        photo.numberOfLikes = photoJson.getJSONObject("likes").getInt("count");
                        photo.imageURL = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photos.add(photo);
                    }

                    photosAdapter.notifyDataSetChanged();

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
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
