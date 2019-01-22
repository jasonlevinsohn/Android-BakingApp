package com.llamasontheloosefarm.bakingapp2.data;

public class Recipe {
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
}
