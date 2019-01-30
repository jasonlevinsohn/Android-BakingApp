package com.llamasontheloosefarm.bakingapp2.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import java.sql.Array;

public final class RecipeJSONUtils {

    public static Recipe[] getRecipesFromJSON(Context context, String recipeJSON) throws JSONException {

        final String RECIPES_STATUS_CODE = "cod";
        final String INGREDIENTS_LIST = "ingredients";
        final String STEPS_LIST = "steps";


        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_IMAGE = "image";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";

        final String INGREDIENT_QUANTITY = "quantity";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT_INGREDIENT = "ingredient";

        final String STEP_ID = "id";
        final String STEP_SHORT_DESCRIPTION = "shortDescription";
        final String STEP_DESCRIPTION = "description";
        final String STEP_VIDEO_URL = "videoURL";
        final String STEP_THUMBNAIL_URL = "thumbnailURL";

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

            // Get Ingredients
            JSONArray ingredientsJSONArray = recipeObj.getJSONArray(INGREDIENTS_LIST);
            RecipeIngredient[] ingredients = new RecipeIngredient[ingredientsJSONArray.length()];

            JSONArray stepsJSONArray = recipeObj.getJSONArray(STEPS_LIST);
            RecipeStep[] steps = new RecipeStep[stepsJSONArray.length()];

            for (int j = 0; j < ingredientsJSONArray.length(); j++) {
                RecipeIngredient ingredient;
                JSONObject ingredientJSON = ingredientsJSONArray.getJSONObject(j);
                int quantity = ingredientJSON.getInt(INGREDIENT_QUANTITY);
                String measure = ingredientJSON.getString(INGREDIENT_MEASURE);
                String ingred = ingredientJSON.getString(INGREDIENT_INGREDIENT);

                ingredient = new RecipeIngredient(quantity, measure, ingred);
                ingredients[j] = ingredient;
            }

            for (int j = 0; j < stepsJSONArray.length(); j++) {
                RecipeStep step;
                JSONObject stepJSON = stepsJSONArray.getJSONObject(j);
                int stepId = stepJSON.getInt(STEP_ID);
                String shortDesc = stepJSON.getString(STEP_SHORT_DESCRIPTION);
                String desc = stepJSON.getString(STEP_DESCRIPTION);
                String videoUrl = stepJSON.getString(STEP_VIDEO_URL);
                String thumbnailUrl = stepJSON.getString(STEP_THUMBNAIL_URL);

                step = new RecipeStep(stepId, shortDesc, desc, videoUrl, thumbnailUrl);
                steps[j] = step;
            }

            Recipe recipe = new Recipe(recipeId, recipeName, ingredients, steps);
//            Recipe recipe = new Recipe(recipeId, recipeName, ingredients);

            parsedRecipeData[i] = recipe;

        }

        return parsedRecipeData;

    }

}
