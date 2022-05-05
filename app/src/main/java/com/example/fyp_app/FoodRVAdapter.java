package com.example.fyp_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodRVAdapter extends RecyclerView.Adapter<FoodRVAdapter.ViewHolder> {

    // variable for our array list and context.
    private ArrayList<FoodModal> userModalArrayList;
    private Context context;

    // creating a constructor.
    public FoodRVAdapter(ArrayList<FoodModal> userModalArrayList, Context context) {
        this.userModalArrayList = userModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.food_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // getting data from our array list in our modal class.
        FoodModal userModal = userModalArrayList.get(position);

        // on below line we are setting data to our text view.
        holder.foodRecommendTitle.setText(userModal.getTitle());

        holder.caloriesKcal.setText(userModal.getCalories().toString()+"kcal");
        holder.idTVProteinAmount.setText(userModal.getProtein().toString()+"g");
        holder.idTVFatAmount.setText(userModal.getFat().toString()+"g");
        holder.idTVCarbohydratesAmount.setText(userModal.getCarbohydrates().toString()+"g");

        // on below line we are loading our image
        // from url in our image view using picasso.
        Picasso.get().load(userModal.getImage()).into(holder.foodRecommendImage);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return userModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView foodRecommendTitle, idTVProteinAmount, caloriesKcal,idTVFatAmount,idTVCarbohydratesAmount;
        private ImageView foodRecommendImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our variables.
            foodRecommendTitle = itemView.findViewById(R.id.foodRecommendTitle);
            foodRecommendImage = itemView.findViewById(R.id.foodRecommendImage);

            caloriesKcal = itemView.findViewById(R.id.caloriesKcal);
            idTVProteinAmount = itemView.findViewById(R.id.idTVProteinAmount);
            idTVFatAmount = itemView.findViewById(R.id.idTVFatAmount);
            idTVCarbohydratesAmount = itemView.findViewById(R.id.idTVCarbohydratesAmount);
        }
    }
}
