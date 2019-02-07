package com.llamasontheloosefarm.bakingapp2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import java.util.ArrayList;

public class SingleStepFragment extends Fragment {

    public static final String STEP_INDEX = "step_index";
    public static final String STEP_LIST = "step_list";

    private RecipeStep mStep;
    private int mStepIndex;
    private ArrayList<RecipeStep> mStepList;
    private View rootView;

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
        final TextView testTV = (TextView) rootView.findViewById(R.id.single_step_tv_tester);
        mStep = mStepList.get(mStepIndex);
        if (mStep != null) {
            testTV.setText(mStep.getShortDescription());
        } else {
            testTV.setText("You and me Hommie");
        }

        testTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStepIndex < mStepList.size()-1) {
                    mStepIndex++;
                } else {
                    mStepIndex = 0;
                }

                mStep = mStepList.get(mStepIndex);
                testTV.setText(mStep.getShortDescription());
            }
        });

        return rootView;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_LIST, mStepList);
        outState.putInt(STEP_INDEX, mStepIndex);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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
}
