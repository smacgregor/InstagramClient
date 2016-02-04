package com.smacgregor.instagramclient.viewing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.smacgregor.instagramclient.R;
import com.smacgregor.instagramclient.core.InstagramPhoto;
import com.smacgregor.instagramclient.core.InstagramPhotosResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    private final String INSTAGRAM_CLIENTID = "e05c462ebd86446ea48a5af73769b602";

    private List<InstagramPhoto> photos;
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
        params.put("more", "data"); https://api.instagram.com/v1/tags/search?q=snowy&
        client.get("https://api.instagram.com/v1/tags/bentleypup/media/recent/?client_id=" + INSTAGRAM_CLIENTID, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.i("DEBUG", response.toString());

                photos.addAll(InstagramPhotosResponse.parseJSON(response).photos);
                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // Do something useful
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
