package com.llamasontheloosefarm.bakingapp2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.facebook.stetho.Stetho;
import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.utilities.NetworkUtils;
import com.llamasontheloosefarm.bakingapp2.utilities.RecipeJSONUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import timber.log.Timber;


public class RecipeListActivity extends AppCompatActivity {

    private RecipeGridAdapter recipeGridAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        gridView = (GridView) findViewById(R.id.recipe_list_grid_view);

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
        }
    }

    public class FetchRecipeTask extends AsyncTask<String, Void, Recipe[]> {

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

            populateRecipeList(recipes);

        }
    }
}
