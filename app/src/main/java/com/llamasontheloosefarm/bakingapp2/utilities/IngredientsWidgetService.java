package com.llamasontheloosefarm.bakingapp2.utilities;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.llamasontheloosefarm.bakingapp2.IngredientsWidgetProvider;

public class IngredientsWidgetService  extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS_WIDGET = "com.llamasontheloosefarm.bakingapp2.action.update_ingredients";

    public IngredientsWidgetService() {
        super("IngredientsWidgetService");
    }

    public static void startActionUpdateIngredientsList(Context context) {
        Intent intent  = new Intent(context, IngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_INGREDIENTS_WIDGET.equals(action)) {
                handleActionUpdateIngredientsWidget();
            }
        }
    }


    private void handleActionUpdateIngredientsWidget() {
//        Context context = getApplicationContext();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String recipeName = mPrefs.getString("recipe_name", "No Recipe Viewed");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        IngredientsWidgetProvider.updateIngredientsWidget(this, appWidgetManager, recipeName, appWidgetIds);
    }
}
