package com.llamasontheloosefarm.bakingapp2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static SharedPreferences mPrefs;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipeName,
                                int appWidgetId) {

        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
//        String recipeName = mPrefs.getString("recipe_name", "No Recipe Selected");
//        RecipeIngredient[] ingredients;

//        Gson gson = new Gson();
//        String jsonText = mPrefs.getString("ingredients", "No Ingredient");

//        try {
//            ingredients = gson.fromJson(jsonText, RecipeIngredient[].class);
//        } catch (Exception e) {
//            ingredients = new RecipeIngredient[0];
//            e.printStackTrace();
//        }


//        for (int i = 0; i < ingredients.length; i++) {
//            Timber.d("Preference Ingredient: %s", ingredients[i].getIngredient());
//        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, recipeName);

        // Set List View
        Intent listViewIntent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.widget_ingredient_list_view, listViewIntent);

        // Launch Click Handler to open the app from the widget
        Intent appStartIntent = new Intent(context, RecipeListActivity.class);
        PendingIntent launchAppPendingIntent = PendingIntent.getActivity(
                context,
               0,
                appStartIntent,
               0);

        views.setOnClickPendingIntent(R.id.appwidget_text, launchAppPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientsWidget(Context context, AppWidgetManager appWidgetManager, String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
        }
    }

//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
//    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

