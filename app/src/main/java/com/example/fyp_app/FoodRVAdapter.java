package com.example.fyp_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp_app.R;
import com.example.fyp_app.FoodModal;
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
        holder.firstNameTV.setText(userModal.getTitle());
        holder.lastNameTV.setText("Fat");
        holder.emailTV.setText(userModal.getAmount().toString()+"g");

        // on below line we are loading our image
        // from url in our image view using picasso.
        Picasso.get().load(userModal.getImage()).into(holder.userIV);
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return userModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating a variable for our text view and image view.
        private TextView firstNameTV, lastNameTV, emailTV;
        private ImageView userIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our variables.
            firstNameTV = itemView.findViewById(R.id.idTVFirstName);
            lastNameTV = itemView.findViewById(R.id.idTVLastName);
            emailTV = itemView.findViewById(R.id.idTVEmail);
            userIV = itemView.findViewById(R.id.idIVUser);
        }
    }
}
