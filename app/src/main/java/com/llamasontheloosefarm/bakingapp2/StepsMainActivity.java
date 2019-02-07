package com.llamasontheloosefarm.bakingapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import java.util.ArrayList;

import timber.log.Timber;

public class StepsMainActivity  extends AppCompatActivity {

    Parcelable singleStep = null;
    Parcelable[] parcelableStepList = null;
    int mStepIndex;
    ArrayList<RecipeStep> mStepList;

    FragmentManager fragmentManager;
    SingleStepFragment singleStepFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_main);

        // Only create a new fragment if there are no previously saved fragments.
        if (savedInstanceState == null) {

            Intent fromIntent = getIntent();

            if (fromIntent.hasExtra("stepIndex") &&
                    fromIntent.hasExtra("stepsList")) {

                mStepList = fromIntent.getParcelableArrayListExtra("stepsList");
                mStepIndex = fromIntent.getIntExtra("stepIndex", 0);

                singleStepFragment = new SingleStepFragment();
                singleStepFragment.setStepList(mStepList);
                singleStepFragment.setStepIndex(mStepIndex);

                fragmentManager = getSupportFragmentManager();

//                Fragment frag = fragmentManager.findFragmentById(R.id.single_step_container);
//                if (frag != null) {
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.head_container, singleStepFragment)
//                            .commit();
//                } else {
                fragmentManager.beginTransaction()
                        .add(R.id.single_step_container, singleStepFragment)
                        .commit();
//                }


            }

        }


    }

}
