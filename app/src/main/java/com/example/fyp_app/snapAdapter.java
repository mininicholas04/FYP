package com.example.fyp_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class snapAdapter extends RecyclerView.Adapter<snapAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String,String>> spendArray;


    snapAdapter(Context context, ArrayList<HashMap<String,String>> spendArray){
        this.context = context;
        this.spendArray = spendArray;
    }

    public void setSpendArray(ArrayList<HashMap<String, String>> spendArray) {
        this.spendArray = spendArray;
    }

    @Override
    public snapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_snap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull snapAdapter.ViewHolder holder, int position) {
        final HashMap<String,String> a  = spendArray.get(position);
        holder.nutri.setText(a.get("nutri"));
        holder.grams.setText(a.get("grams"));

        holder.

                setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return spendArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nutri, grams;
        ViewHolder(View itemView) {
            super(itemView);
            nutri =  (TextView) itemView.findViewById(R.id.snapNutri_textview);
            grams = (TextView) itemView.findViewById(R.id.snap100g_textview);
        }

        public void setAnimation(View viewToAnimate, int position) {
            int lastPosition=0;
            if (position >= lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                animation.setDuration(700);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
    }
}