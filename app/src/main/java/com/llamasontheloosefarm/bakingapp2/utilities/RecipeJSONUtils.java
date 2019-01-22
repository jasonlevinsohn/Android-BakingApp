package com.llamasontheloosefarm.bakingapp2.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;

public final class RecipeJSONUtils {

    public static Recipe[] getRecipesFromJSON(Context context, String recipeJSON) throws JSONException {

        final String RECIPES_STATUS_CODE = "cod";

        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_IMAGE = "image";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";

        Recipe[] parsedRecipeData = null;

//        JSONObject recipeJson = new JSONObject(recipeJSON);
        JSONArray recipeArray = new JSONArray(recipeJSON);

//        if (recipeJson.has(RECIPES_STATUS_CODE)) {
//            int errorCode = recipeJson.getInt(RECIPES_STATUS_CODE);
//
//            switch(errorCode) {
//                case HttpURLConnection.HTTP_OK:
//                    break;
//                case HttpURLConnection.HTTP_NOT_FOUND:
//                    return null;
//                default:
//                    return null;
//            }
//        }

        parsedRecipeData = new Recipe[recipeArray.length()];

        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject recipeObj = recipeArray.getJSONObject(i);
            int recipeId = recipeObj.getInt(RECIPE_ID);
            String recipeName = recipeObj.getString(RECIPE_NAME);

            Recipe recipe = new Recipe(recipeId, recipeName);

            parsedRecipeData[i] = recipe;

        }

        return parsedRecipeData;

    }

}
