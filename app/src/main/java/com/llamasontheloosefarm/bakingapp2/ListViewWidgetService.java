package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;

import timber.log.Timber;

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    SharedPreferences mPrefs;
    RecipeIngredient[] ingredients;

    public ListRemoteViewsFactory(Context appContext) {
        mContext = appContext;
    }

    @Override
    public void onCreate() {
        Timber.d("Widget Factory is being created");

    }

    @Override
    public void onDataSetChanged() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        String recipeName = mPrefs.getString("recipe_name", "No Recipe Selected");

        Gson gson = new Gson();
        String jsonText = mPrefs.getString("ingredients", "No Ingredient");

        try {
            ingredients = gson.fromJson(jsonText, RecipeIngredient[].class);
        } catch (Exception e) {
            ingredients = new RecipeIngredient[0];
            e.printStackTrace();
        }


        for (int i = 0; i < ingredients.length; i++) {
            Timber.d("Preference Ingredient: %s", ingredients[i].getIngredient());
        }
    }

    @Override
    public void onDestroy() {
        if (ingredients != null) {
            ingredients = null;
        }
    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (ingredients == null || ingredients.length == 0) return null;
        int quantity = ingredients[i].getQuantity();
        String measure = ingredients[i].getMeasure();
        String ingredient = ingredients[i].getIngredient();


        // We will use the ingredient list item view for the list
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);

        views.setTextViewText(R.id.tv_quantity_text, Integer.toString(quantity));
        views.setTextViewText(R.id.tv_measure_text, measure);
        views.setTextViewText(R.id.tv_ingredient_text, ingredient);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the gridview the same.
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
