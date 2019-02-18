package com.llamasontheloosefarm.bakingapp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipeDetailListActivity extends AppCompatActivity {

//    private TextView mName;
//    @BindView(R.id.tv_recipe_test) TextView mName;

    private IngredientStepAdapter mIngredientAdapter;
    private RecyclerView mIngredientRecyclerView;
    private boolean mTwoPane = false;
//    MasterListFragment masterListFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.recipe_detail_list);
        setContentView(R.layout.master_recipe_ingredient_step);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.step_linear_layout) != null) {
            mTwoPane = true;
            Timber.d("THIS IS A TWO PANE LAYOUT");
        } else {
            Timber.d("THIS IS NOT A TWO PANE LAYOUT");
        }


//        mIngredientRecyclerView = (RecyclerView) findViewById(R.id.rv_recipe_detail_list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mIngredientRecyclerView.setLayoutManager(layoutManager);
//        mIngredientRecyclerView.setHasFixedSize(true);


//        masterListFragment = new MasterListFragment();
        MasterListFragment masterListFrag = (MasterListFragment) getSupportFragmentManager().findFragmentById(R.id.master_list_fragment);

        String recipeName;
        Recipe selectedRecipe;
        Parcelable[] parcelableIngreds;
        Parcelable[] parcelableSteps;
        ArrayList<Object> selectedIngreds = new ArrayList<>();
        ArrayList<RecipeStep> stepsList = new ArrayList<>();

        Intent fromIntent = getIntent();

        // Set the title of the activity
        if (fromIntent.hasExtra("recipe")) {
            selectedRecipe = fromIntent.getParcelableExtra("recipe");
            setTitle(selectedRecipe.getName());
        } else {
            selectedRecipe = null;
        }


        if (fromIntent.hasExtra("ingredients")) {
            parcelableIngreds = fromIntent.getParcelableArrayExtra("ingredients");
            if (parcelableIngreds.length != 0) {
                selectedIngreds.add("Ingredients");
                for (Parcelable ingred : parcelableIngreds) {
                    selectedIngreds.add((RecipeIngredient) ingred);
                }
            }
        }

        if (fromIntent.hasExtra("steps")) {
            parcelableSteps = fromIntent.getParcelableArrayExtra("steps");
            if (parcelableSteps.length != 0) {
                selectedIngreds.add("Steps");
                for (Parcelable step : parcelableSteps) {
                    selectedIngreds.add((RecipeStep) step);
                    stepsList.add((RecipeStep) step);
                }
            }
        }

        masterListFrag.setSelectedIngreds(selectedIngreds);
        masterListFrag.setStepsList(stepsList);
        masterListFrag.setRecipe(selectedRecipe);
        masterListFrag.setTwoPane(mTwoPane);

        // After setting variables, set the adapter
        masterListFrag.setAdapter();


//        if (!selectedIngreds.isEmpty()) {
//            Context context = RecipeDetailListActivity.this;
//            Activity activity = RecipeDetailListActivity.this;
//
//            mIngredientAdapter = new IngredientStepAdapter(context, selectedRecipe, selectedIngreds, stepsList);
//        }
//
//        mIngredientRecyclerView.setAdapter(mIngredientAdapter);


//        mName = (TextView) findViewById(R.id.tv_recipe_test);
//        mName.setText(selectedRecipe.getName());

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //    @Override
//    public void onStepSelected(int position) {
//        Timber.d("Step position clicked: %d", position);
//    }
}
