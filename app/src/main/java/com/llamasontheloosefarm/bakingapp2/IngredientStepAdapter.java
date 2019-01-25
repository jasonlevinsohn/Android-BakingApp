package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;

import java.util.ArrayList;

public class IngredientStepAdapter extends RecyclerView.Adapter<IngredientStepAdapter.IngredientStepViewHolder> {


    private Recipe mRecipe;
    private ArrayList<RecipeIngredient> mIngredients;


    public IngredientStepAdapter(Recipe recipe, ArrayList<RecipeIngredient> ingredients) {
        mRecipe = recipe;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        IngredientStepViewHolder viewHolder = new IngredientStepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientStepViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class IngredientStepViewHolder extends RecyclerView.ViewHolder {
        TextView listItemIngredient;
        TextView listItemQuantity;
        TextView listItemMeasure;

        public IngredientStepViewHolder(View itemView) {
            super(itemView);

            listItemIngredient = (TextView) itemView.findViewById(R.id.tv_ingredient_text);
            listItemQuantity = (TextView) itemView.findViewById(R.id.tv_quantity_text);
            listItemMeasure = (TextView) itemView.findViewById(R.id.tv_measure_text);
        }

        void bind(int posIndex) {
            String ingred = mIngredients.get(posIndex).getIngredient();
            int quantity = mIngredients.get(posIndex).getQuantity();
            String measure = mIngredients.get(posIndex).getMeasure();

            listItemIngredient.setText(ingred);
            listItemQuantity.setText(Integer.toString(quantity));
            listItemMeasure.setText(measure);

        }

    }

}
