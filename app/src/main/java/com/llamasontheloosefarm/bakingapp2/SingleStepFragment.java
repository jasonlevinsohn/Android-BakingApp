package com.llamasontheloosefarm.bakingapp2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SingleStepFragment extends Fragment {

    public SingleStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the single step fragment
        View rootView = inflater.inflate(R.layout.fragment_single_step, container, false);


        // Get a reference to the Test Text View in the fragment
        TextView testTV = (TextView) rootView.findViewById(R.id.single_step_tv_tester);
        testTV.setText("You and me Hommie");

        return rootView;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
