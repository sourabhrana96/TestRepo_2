package tech.mohitkumar.recylcerexoplayer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;

import java.util.ArrayList;

import tech.mohitkumar.recylcerexoplayer.Adapters.RecyclerVideoAdapter;
import tech.mohitkumar.recylcerexoplayer.CustomView.SnappingRecyclerView;
import tech.mohitkumar.recylcerexoplayer.Interface.VideoFinished;

public class MainActivity extends AppCompatActivity{

    Handler handler;
    Timeline.Window window;
    SimpleExoPlayerView simpleExoPlayerView;
    DataSource.Factory factory;
    SimpleExoPlayer player;
    DefaultTrackSelector trackSelector;

    RecyclerView recyclerView;
    RecyclerVideoAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<String> arraylist = new ArrayList<String>();

    boolean shouldAutoPlay;
    int playerWindow;
    long playerPosition;
    int pos;
    BandwidthMeter bandwidthMeter;
    DefaultExtractorsFactory defaultExtractorsFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Log.d("HEIGHT",Integer.toString(height));

        arraylist.add("http://playertest.longtailvideo.com/adaptive/bbbfull/bbbfull.m3u8");
        arraylist.add("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8");
        arraylist.add("https://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/bipbop_16x9_variant.m3u8");
        arraylist.add("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8");
        arraylist.add("http://playertest.longtailvideo.com/adaptive/bbbfull/bbbfull.m3u8");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerVideoAdapter(arraylist,getApplicationContext());
        recyclerView.setAdapter(adapter);

      //  SnapHelper snapHelper = new LinearSnapHelper();
      ///   snapHelper.attachToRecyclerView(recyclerView);

        adapter.setVideoFinished(new VideoFinished() {
            @Override
            public void onVideoFinished(int position) {
                Log.d("TAG","GOT THE CALLBACk" + recyclerView.getChildCount());
                recyclerView.scrollToPosition(position+1);
            }

            @Override
            public void onInteraction(int position) {
                pos = position;
                Log.d("POSITION",Integer.toString(pos));
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public boolean top;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView
                        .getLayoutManager());

                layoutManager.findLastCompletelyVisibleItemPosition();

                if (top) {
                    int index = layoutManager.findFirstVisibleItemPosition();
                    recyclerView.smoothScrollToPosition(index);

                } else {
                    int index = layoutManager.findLastVisibleItemPosition();
                    recyclerView.smoothScrollToPosition(index);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    top = false;
                } else {
                    top = true;
                }

            }
        });
       // Intent intent = new Intent(MainActivity.this, CustomVideoPlayerActivity.class);
       // startActivity(intent);
    }

}
