package com.llamasontheloosefarm.bakingapp2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.quantity);
        parcel.writeString(this.measure);
        parcel.writeString(this.ingredient);
    }

    protected RecipeIngredient(Parcel in) {
        this.quantity = in.readInt();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel parcel) {
            return new RecipeIngredient(parcel);
        }

        @Override
        public RecipeIngredient[] newArray(int i) {
            return new RecipeIngredient[i];
        }
    };
}
