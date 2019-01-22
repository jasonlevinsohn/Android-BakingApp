package com.llamasontheloosefarm.bakingapp2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;

import java.util.ArrayList;

import timber.log.Timber;

public class RecipeGridAdapter extends ArrayAdapter<Recipe> {

    public RecipeGridAdapter(Activity context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = getItem(position);
        TextView nameTV;

        String name = recipe.getName();

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);

            nameTV = (TextView) convertView.findViewById(R.id.recipe_list_item_name);
            Timber.d("Recipe Name: %s", name);
            nameTV.setText(name);
        }

        return convertView;

    }
}
