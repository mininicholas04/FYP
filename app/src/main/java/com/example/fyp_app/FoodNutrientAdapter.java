package com.example.fyp_app;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class FoodNutrientAdapter extends RecyclerView.Adapter<FoodNutrientAdapter.ViewHolder> {

    private static JSONArray localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodName;
        private final TextView foodGram;
        private final TextView foodRank;
        private final ImageButton moreInfo;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            foodName = (TextView) view.findViewById(R.id.foodName);
            foodGram = (TextView) view.findViewById(R.id.foodGram);
            foodRank = (TextView) view.findViewById(R.id.foodRank);
            moreInfo = (ImageButton) view.findViewById(R.id.moreInfo);
            ViewParent v = ((View) foodName).getParent();
            ((LinearLayout) v).setBaselineAligned(false);

            itemView.findViewById(R.id.moreInfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(itemView.getContext(), com.example.fyp_app.FoodNutrientInfoActivity.class);
                        String temp  = localDataSet.getJSONObject(getAdapterPosition()).toString();
                        intent.putExtra("data",temp);
                        itemView.getContext().startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public TextView getFoodName() {
            return foodName;
        }
        public TextView getFoodGram() {
            return foodGram;
        }
        public TextView getFoodRank() {
            return foodRank;
        }
        public ImageButton getMoreInfo() {
            return moreInfo;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public FoodNutrientAdapter(JSONArray dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_nutrient, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try{
            viewHolder.getFoodRank().setText(""+(position+1));
            viewHolder.getFoodName().setText(localDataSet.getJSONObject(position).getString("title"));
            viewHolder.getFoodGram().setText(localDataSet.getJSONObject(position).getString("vitaminA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        try  {
            return localDataSet.length();
        }catch (NullPointerException e) {
            System.out.println("Your daily points limit of 150 has been reached");
        }
        return 0;
    }
}