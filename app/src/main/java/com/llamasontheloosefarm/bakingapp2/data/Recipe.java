package com.llamasontheloosefarm.bakingapp2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private RecipeIngredient[] ingredients;
    private RecipeStep[] steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, RecipeIngredient[] ingredients, RecipeStep[] steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Recipe(int id, String name, RecipeIngredient[] ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Recipe(int id, String name, RecipeIngredient[] ingredients, RecipeStep[] steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RecipeIngredient[] getIngredients() {
        return ingredients;
    }

    public RecipeStep[] getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
//        parcel.writeTypedArray(this.ingredients, 0);
//        parcel.writeTypedArray(this.steps, 0);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
//        if (this.ingredients != null) {
//            in.readTypedArray(this.ingredients, RecipeIngredient.CREATOR);
//        }
//        if (this.steps != null) {
//            in.readTypedArray(this.steps, RecipeStep.CREATOR);
//        }
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };
}
