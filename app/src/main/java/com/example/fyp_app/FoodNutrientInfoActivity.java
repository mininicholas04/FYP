package com.example.fyp_app;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodNutrientInfoActivity extends AppCompatActivity {

    RecyclerView recyclerView2;
    TextView foodTitle;
    ImageView foodPicture;
    TextView foodNutrientName;
    TextView foodNutrientGram;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_nutrient_info);
        recyclerView2 = findViewById(R.id.recyclerView_Nutrient);
        foodTitle = findViewById(R.id.foodTitle);
        foodPicture = findViewById(R.id.foodPicture);
        foodNutrientName = (TextView) findViewById(R.id.foodNutrientName);
        foodNutrientGram = (TextView) findViewById(R.id.foodNutrientGram);

        String str = getIntent().getStringExtra("data");
        Log.v("data",str);
        try {
            JSONObject jsonObject = new JSONObject(str);
            String temp = jsonObject.getString("title");
            Uri imgUri=Uri.parse(jsonObject.getString("image"));
            Picasso.get().load(imgUri).into(foodPicture);
            foodPicture.setImageURI(imgUri);
            //Log.v("data",temp);
            foodTitle.setText(temp);

            FoodNutrientInfoAdapter adapter = new FoodNutrientInfoAdapter(jsonObject);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView2.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
