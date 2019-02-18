package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import java.util.ArrayList;

import timber.log.Timber;

public class SingleStepFragment extends Fragment {

    public static final String STEP_INDEX = "step_index";
    public static final String STEP_LIST = "step_list";

    private RecipeStep mStep;
    private int mStepIndex;
    private ArrayList<RecipeStep> mStepList;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    private View dividerView;
    private TextView longDescTV;

    public SingleStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mStepList = savedInstanceState.getParcelableArrayList(STEP_LIST);
            mStepIndex = savedInstanceState.getInt(STEP_INDEX);
        }

        // Inflate the single step fragment
        View rootView = inflater.inflate(R.layout.fragment_single_step, container, false);

        // Get a reference to the Test Text View in the fragment
//        final TextView testTV = (TextView) rootView.findViewById(R.id.single_step_tv_tester);
//        TextView mediaTV = (TextView) rootView.findViewById(R.id.tv_video_url);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        longDescTV = (TextView) rootView.findViewById(R.id.tv_long_description);
        dividerView = (View) rootView.findViewById(R.id.divider);

        // Load Play button until Video starts playing
        mExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.exo_controls_play));

        mStep = mStepList.get(mStepIndex);
        if (mStep != null) {
            Uri mediaUri;
            if(!mStep.getVideoURL().isEmpty()) {
//                mediaTV.setText(mStep.getVideoURL());
                mediaUri = Uri.parse(mStep.getVideoURL());
            } else if (!mStep.getThumbnailURL().isEmpty()) {
//                mediaTV.setText(mStep.getThumbnailURL());
                mediaUri = Uri.parse(mStep.getThumbnailURL());
            } else {
                mediaUri = null;
            }

            if (mediaUri != null) {
                initializePlayer(mediaUri);
            }

//            testTV.setText(mStep.getShortDescription());
            longDescTV.setText(mStep.getDescription());

        }

//        testTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mStepIndex < mStepList.size()-1) {
//                    mStepIndex++;
//                } else {
//                    mStepIndex = 0;
//                }
//
//                mStep = mStepList.get(mStepIndex);
//                testTV.setText(mStep.getShortDescription());
//            }
//        });

        return rootView;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        releasePlayer();

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mExoPlayerView.getLayoutParams();
//
//        Timber.d("ORIENTATION WIDTH: %d", params.width);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            params.width = params.MATCH_PARENT;
//            params.height = params.MATCH_PARENT;
//
//            longDescTV.setVisibility(View.INVISIBLE);
//            dividerView.setVisibility(View.INVISIBLE);
//            mExoPlayerView.setLayoutParams(params);
//        } else {
//            params.height=400;
//
//            longDescTV.setVisibility(View.VISIBLE);
//            dividerView.setVisibility(View.VISIBLE);
//            mExoPlayerView.setLayoutParams(params);
//        }
//
//
//    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            Context context = getActivity();

            // Create instance of Exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mExoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            mExoPlayerView.setPlayer(mExoPlayer);

            // Prepare media source
            String userAgent = Util.getUserAgent(context, "BakingApp2");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, mStepList);
        outState.putInt(STEP_INDEX, mStepIndex);
    }

    public void setMStep(RecipeStep step) {
        mStep = step;
    }
    public void setStepIndex(int stepIndex) {
        mStepIndex = stepIndex;
    }

    public void setStepList(ArrayList<RecipeStep> stepList) {
        mStepList = stepList;
    }

    public void changeOrientationConfig(boolean isLandScape, Configuration newConfig) {

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mExoPlayerView.getLayoutParams();
        Timber.d("ORIENTATION WIDTH: %d", params.width);
        if (isLandScape) {
//            params.width = params.MATCH_PARENT;
//            int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newConfig.screenHeightDp, getResources().getDisplayMetrics());
//            params.height = heightInDp;

//            Timber.d("Screen Height in landscape: %d", newConfig.screenHeightDp);
//            Timber.d("Screen Height in landscape in dp: %d", heightInDp);

            longDescTV.setVisibility(View.INVISIBLE);
            dividerView.setVisibility(View.INVISIBLE);
            mExoPlayerView.setLayoutParams(params);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else {
            int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
            params.height=heightInDp;

            longDescTV.setVisibility(View.VISIBLE);
            dividerView.setVisibility(View.VISIBLE);
            mExoPlayerView.setLayoutParams(params);
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }
}
