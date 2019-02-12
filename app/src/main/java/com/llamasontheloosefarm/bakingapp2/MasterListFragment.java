package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import java.util.ArrayList;

import butterknife.ButterKnife;
import timber.log.Timber;

public class MasterListFragment extends Fragment {


    // OnStepClickListener interface, calls a method in the host activity
    // named onStepSelected
//    OnStepClickListener mStepCallback;
//
//    public interface OnStepClickListener {
//        void onStepSelected(int position);
//    }

    // Let's make sure the container(host) activity has implemented the OnStepClickListener
    // callback.
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        // This makes sure the host activity has implemented the callback interface
//        try {
//            mStepCallback = (OnStepClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement OnStepClickListener");
//        }
//    }

    public static final String STEP_INDEX = "step_index";

    private IngredientStepAdapter mIngredientAdapter;
    private RecyclerView mIngredientRecyclerView;


    private Recipe mSelectedRecipe;
    private ArrayList<Object> mSelectedIngreds = new ArrayList<>();
    private ArrayList<RecipeStep> mStepsList = new ArrayList<>();
    private Context mContext;
    private boolean mTwoPane = false;

    public MasterListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();

        if (savedInstanceState != null) {
            // Populate from saved instance state with these bad boys
            // someMemberVariable = savedInstanceState.getParcelableArrayList
        }

        View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);

        mIngredientRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_detail_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mIngredientRecyclerView.setLayoutManager(layoutManager);
        mIngredientRecyclerView.setHasFixedSize(true);



        return rootView;
    }

//    public void setMStep(RecipeStep step) {
//        mStep = step;
//    }
//    public void setStepIndex(int stepIndex) {
//        mStepIndex = stepIndex;
//    }
    public void setTwoPane(boolean twoPane) {
        mTwoPane = twoPane;
    }

    public void setAdapter() {
        if (!mSelectedIngreds.isEmpty()) {
            mIngredientAdapter = new IngredientStepAdapter(mContext, mSelectedRecipe, mSelectedIngreds, mStepsList, mTwoPane);
            mIngredientRecyclerView.setAdapter(mIngredientAdapter);

//            mIngredientRecyclerView.setOnClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//            });

            mIngredientRecyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Timber.d("An item was clicked in the recycler view Step");

                }
            });

            if (mTwoPane) {
                SingleStepFragment stepFragment = new SingleStepFragment();
                stepFragment.setStepList(mStepsList);
                stepFragment.setStepIndex(0);

                FragmentManager fragManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                fragManager.beginTransaction()
                        .add(R.id.media_container, stepFragment)
                        .commit();

            }

        }

    }

    public void setRecipe(Recipe recipe) {
        mSelectedRecipe = recipe;
    }

    public void setStepsList(ArrayList<RecipeStep> stepsList) {
        mStepsList = stepsList;
    }

    public void setSelectedIngreds(ArrayList<Object> selectedIngreds) {
        mSelectedIngreds = selectedIngreds;
    }

}



