package com.llamasontheloosefarm.bakingapp2.data;

public class RecipeIngredient {
    private int quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}
