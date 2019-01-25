package com.llamasontheloosefarm.bakingapp2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;

import java.util.ArrayList;

import timber.log.Timber;

public class RecipeDetailListActivity extends AppCompatActivity {

    private TextView mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_list);

        String recipeName;
        Recipe selectedRecipe;
        Parcelable[] parcelableIngreds;
        ArrayList<RecipeIngredient> selectedIngreds;

        Intent fromIntent = getIntent();

        if (fromIntent.hasExtra("recipe")) {
            selectedRecipe = fromIntent.getParcelableExtra("recipe");

        } else {
            selectedRecipe = null;
        }

        if (fromIntent.hasExtra("ingredients")) {
            parcelableIngreds = fromIntent.getParcelableArrayExtra("ingredients");
            if (parcelableIngreds.length != 0) {
                selectedIngreds = new ArrayList<>();
                for (Parcelable ingred : parcelableIngreds) {
                    selectedIngreds.add((RecipeIngredient) ingred);
                }
            }
        }

        mName = (TextView) findViewById(R.id.tv_recipe_test);
        mName.setText(selectedRecipe.getName());

    }
}
