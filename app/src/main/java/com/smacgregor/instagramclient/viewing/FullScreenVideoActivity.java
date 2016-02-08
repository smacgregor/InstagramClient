package com.smacgregor.instagramclient.viewing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

import com.smacgregor.instagramclient.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullScreenVideoActivity extends Activity {

    private static final String EXTRA_VIDEO_URL = "com.smacgregor.instagramclient.viewing.video_url";

    @Bind(R.id.video_view) VideoView videoView;

    public static Intent getStartIntent(Context context, final String videoURL) {
        Intent intent = new Intent(context, FullScreenVideoActivity.class);
        intent.putExtra(FullScreenVideoActivity.EXTRA_VIDEO_URL, videoURL);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen_video);
        ButterKnife.bind(this);

        playVideo(getIntent().getStringExtra(FullScreenVideoActivity.EXTRA_VIDEO_URL));
    }

    private void playVideo(final String videoURL) {
        videoView.setVideoPath(videoURL);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });

    }
}
