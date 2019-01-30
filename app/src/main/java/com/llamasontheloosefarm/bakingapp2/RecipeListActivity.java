package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;
import com.llamasontheloosefarm.bakingapp2.utilities.NetworkUtils;
import com.llamasontheloosefarm.bakingapp2.utilities.RecipeJSONUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class RecipeListActivity extends AppCompatActivity {

    private RecipeGridAdapter recipeGridAdapter;
//    private GridView gridView;
//    private ProgressBar progressBar;
    @BindView(R.id.recipe_list_grid_view) GridView gridView;
    @BindView(R.id.pb_loading_indicator) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

//        gridView = (GridView) findViewById(R.id.recipe_list_grid_view);
//        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        new FetchRecipeTask().execute();

        // Setup Timber Logger
        Timber.plant(new Timber.DebugTree());

        // Setup Stetho Debug Tool Integration
        Stetho.initializeWithDefaults(this);

    }

    private void populateRecipeList(Recipe[] recipeData) {

        final ArrayList<Recipe> recipeArrayList;

        if (recipeData != null) {

            recipeArrayList = new ArrayList<Recipe>(Arrays.asList(recipeData));
            recipeGridAdapter = new RecipeGridAdapter(RecipeListActivity.this, recipeArrayList);
            gridView.setAdapter(recipeGridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Recipe recipe = recipeArrayList.get(i);
                    Timber.d("Clicked on Recipe: %s", recipe.getName());
                    RecipeIngredient[] ingreds = recipe.getIngredients();
                    RecipeStep[] steps = recipe.getSteps();

                    for (int j = 0; j < ingreds.length; j++) {
                        Timber.d("Ingredient: %s", ingreds[j].getIngredient());

                    }

                    if (steps != null) {
                        for (int j = 0; j < steps.length; j++) {
                            Timber.d("Steps: %s", steps[j].getDescription());
                        }
                    }

                    Context context = RecipeListActivity.this;
                    Class dest = RecipeDetailListActivity.class;

                    Intent recipeDetailListIntent = new Intent(context, dest);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("recipe", recipe);
                    bundle.putParcelableArray("ingredients", ingreds);
                    if (steps != null) {
                        bundle.putParcelableArray("steps", steps);
                    }
//                    recipeDetailListIntent.putExtra("recipe", recipe);
                    recipeDetailListIntent.putExtras(bundle);

                    startActivity(recipeDetailListIntent);
                }
            });
        }
    }

    public class FetchRecipeTask extends AsyncTask<String, Void, Recipe[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Recipe[] doInBackground(String... strings) {

            URL recipeUrl = NetworkUtils.buildUrl(RecipeListActivity.this);

            try {
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
                Timber.d("The returned Recipe JSON");
                Timber.d(jsonRecipeResponse);
                Recipe[] recipeModel = RecipeJSONUtils.getRecipesFromJSON(RecipeListActivity.this, jsonRecipeResponse);
                Timber.d("RecipeObject Gotten");
                Timber.d(recipeModel.toString());

                return recipeModel;
            } catch (Exception e) {
                Timber.d("Error Fetching Recipes");
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {

            progressBar.setVisibility(View.INVISIBLE);
            gridView.setVisibility(View.VISIBLE);

            populateRecipeList(recipes);

        }
    }
}
