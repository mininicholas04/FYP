package com.example.fyp_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

public class FoodNutrientInfoAdapter extends RecyclerView.Adapter<FoodNutrientInfoAdapter.ViewHolder2> {
    private static JSONObject localDataset;
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> gram = new ArrayList<String>();

    public FoodNutrientInfoAdapter(JSONObject jsonObject) {
        localDataset = jsonObject;
        localDataset.remove("id");
        localDataset.remove("title");
        localDataset.remove("image");
        localDataset.remove("imageType");
        String temp = localDataset.toString();
        temp = temp.replace("{","");
        temp = temp.replace("}","");
        String[] dataArray = temp.split(",");
        for (int i = 0; i < dataArray.length; i++) {
            /*if (i == 0 || i == 1 || i == 2 || i == 3 || i == 8) {
                continue;
            }*/
            System.out.println(dataArray[i]);
            String strNew = dataArray[i].replace("\"", "");
            String[] tempStrings = strNew.split(":");

            title.add(tempStrings[0]);
            gram.add(tempStrings[1]);
        }
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_nutrient_info, viewGroup, false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder2 viewHolder, int position) {
        System.out.println(title.get(position));
        viewHolder.getFoodNutrientName().setText(title.get(position));
        System.out.println(gram.get(position));
        viewHolder.getFoodNutrientGram().setText(gram.get(position));
    }


    @Override
    public int getItemCount() {
        try  {
            return title.size();
        }catch (NullPointerException e) {
            System.out.println("Your daily points limit of 150 has been reached");
        }
        return 0;
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder{
        private final TextView foodNutrientName;
        private final TextView foodNutrientGram;
        public  ViewHolder2(View view){
            super(view);
            foodNutrientName = (TextView) view.findViewById(R.id.foodNutrientName);
            foodNutrientGram = (TextView) view.findViewById(R.id.foodNutrientGram);
        }
        public TextView getFoodNutrientName() {
            return foodNutrientName;
        }
        public TextView getFoodNutrientGram() {
            return foodNutrientGram;
        }
    }


}