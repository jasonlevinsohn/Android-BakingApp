package com.llamasontheloosefarm.bakingapp2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.llamasontheloosefarm.bakingapp2.data.Recipe;
import com.llamasontheloosefarm.bakingapp2.data.RecipeIngredient;
import com.llamasontheloosefarm.bakingapp2.data.RecipeStep;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class IngredientStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER = 0;
    private final int INGREDIENT = 1;
    private final int STEP = 2;


    private Recipe mRecipe;
    private ArrayList<Object> mIngredientsAndSteps;
    final private Context mContext;


    public IngredientStepAdapter(Context context, Recipe recipe, ArrayList<Object> ingredientsAndSteps) {
        mRecipe = recipe;
        mContext = context;
        mIngredientsAndSteps = ingredientsAndSteps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder viewHolder;

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case INGREDIENT:
                View vIngredient = inflater.inflate(R.layout.ingredient_list_item, parent, false);
                viewHolder = new IngredientViewHolder(vIngredient);
                break;
            case HEADER:
                View vHeader = inflater.inflate(R.layout.header_list_item, parent, false);
                viewHolder = new HeaderViewHolder(vHeader);
                break;
            case STEP:
                View vStep = inflater.inflate(R.layout.step_list_item, parent, false);
                viewHolder = new StepViewHolder(vStep);
                vStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int position = viewHolder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Timber.d("YOU ARE CLICKING ON A STEP %d", position);
                            try {
                                RecipeStep step = (RecipeStep) mIngredientsAndSteps.get(position);

                                Context IngredientStepAdapterContext = mContext;
                                Class dest = StepsMainActivity.class;

                                Intent activityStepsMainIntent = new Intent(IngredientStepAdapterContext, dest);
                                mContext.startActivity(activityStepsMainIntent);

                                // TODO: We are just trying to get this thing going here.
                                // TODO Look into fragments before going any further
                                Timber.d("Step: %d %s ", step.getId(), step.getDescription());
                            } catch (Exception e) {
                                Timber.d("Selecting proper step error: %s", e.getMessage());
                            }
                        }
                    }
                });
                break;
            default:
                viewHolder = null;
                break;
        }
//        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);
//        IngredientStepViewHolder viewHolder = new IngredientStepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case HEADER:
                HeaderViewHolder headerVH = (HeaderViewHolder) holder;
                configureHeaderViewHolder(headerVH, position);
                break;
            case INGREDIENT:
                IngredientViewHolder ingredientVH = (IngredientViewHolder) holder;
                configureIngredientViewHolder(ingredientVH, position);
                break;
            case STEP:
                StepViewHolder stepVH = (StepViewHolder) holder;
                configureStepViewHolder(stepVH, position);
                break;
            default:
                Log.d("COMPLEX VIEW HOLDER", "Could not bind");
                break;

        }


    }

    @Override
    public int getItemCount() {
        return mIngredientsAndSteps.size();
    }

    @Override
    public int getItemViewType(int position) {
       if (mIngredientsAndSteps.get(position) instanceof RecipeIngredient) {
           return INGREDIENT;
       } else if (mIngredientsAndSteps.get(position) instanceof String) {
           return HEADER;
       } else if (mIngredientsAndSteps.get(position) instanceof RecipeStep) {
           return STEP;
       }

       return -1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_section_header) TextView listItemsectionHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getSectionHeaderTV() {
            return listItemsectionHeader;
        }

        public void setSectionHeaderTV(TextView sectionTV) {
            this.listItemsectionHeader = sectionTV;
        }

    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_step_number) TextView listItemStepNumber;
        @BindView(R.id.tv_step_short_desc) TextView listItemStepDesc;


        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            listItemStepNumber = (TextView) itemView.findViewById(R.id.tv_step_number);
//            listItemStepDesc = (TextView) itemView.findViewById(R.id.tv_step_short_desc);
        }

        public TextView getStepNumberTV() {
            return listItemStepNumber;
        }

        public void setStepNumberTV(TextView stepTV) {
            this.listItemStepNumber = stepTV;
        }

        public TextView getStepDescTV() {
            return listItemStepDesc;
        }

        public void setStepDescTV(TextView stepTV) {
            this.listItemStepDesc = stepTV;
        }
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_text) TextView listItemIngredient;
        @BindView(R.id.tv_quantity_text) TextView listItemQuantity;
        @BindView(R.id.tv_measure_text) TextView listItemMeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            listItemIngredient = (TextView) itemView.findViewById(R.id.tv_ingredient_text);
//            listItemQuantity = (TextView) itemView.findViewById(R.id.tv_quantity_text);
//            listItemMeasure = (TextView) itemView.findViewById(R.id.tv_measure_text);
        }

        public TextView getIngredientTV() {
            return listItemIngredient;
        }

        public void setIngredientTV(TextView ingredTV) {
            this.listItemIngredient = ingredTV;

        }

        public TextView getQuantityTV() {
            return listItemQuantity;
        }

        public void setQuantityTV(TextView quantityTV) {
            this.listItemQuantity = quantityTV;
        }

        public TextView getMeasureTV() {
            return listItemMeasure;
        }

        public void setMeasureTV(TextView measureTV) {
            this.listItemMeasure = measureTV;
        }
//        void bind(int posIndex) {
//            String ingred = mIngredientsAndSteps.get(posIndex).getIngredient();
//            int quantity = mIngredients.get(posIndex).getQuantity();
//            String measure = mIngredients.get(posIndex).getMeasure();
//
//            listItemIngredient.setText(ingred);
//            listItemQuantity.setText(Integer.toString(quantity));
//            listItemMeasure.setText(measure);
//
//        }

    }

    private void configureIngredientViewHolder(IngredientViewHolder vh, int position) {
        RecipeIngredient ingredient = (RecipeIngredient) mIngredientsAndSteps.get(position);
        if (ingredient != null) {
            vh.getIngredientTV().setText(ingredient.getIngredient());
            vh.getQuantityTV().setText(Integer.toString(ingredient.getQuantity()));
            vh.getMeasureTV().setText(ingredient.getMeasure());
        }
    }

    private void configureHeaderViewHolder(HeaderViewHolder vh, int position) {
        String header = mIngredientsAndSteps.get(position).toString();
        if (header != null) {
            vh.getSectionHeaderTV().setText(header);
        }
    }

    private void configureStepViewHolder(StepViewHolder vh, int position) {
        RecipeStep step = (RecipeStep) mIngredientsAndSteps.get(position);
        if (step != null) {
            vh.getStepNumberTV().setText(Integer.toString(step.getId()) + ". ");
            vh.getStepDescTV().setText(step.getShortDescription());

        }
    }

}
