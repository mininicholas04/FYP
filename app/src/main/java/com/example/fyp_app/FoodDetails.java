package com.example.fyp_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
    private Intent intent;
    private TextView foodname_textView;
    private TextView ingred_textView;
    private TextView quan_textView;
    private ImageView nutri_imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snap_details);
        foodname_textView = findViewById(R.id.snap_food);
        ingred_textView = findViewById(R.id.nutri_descri);
        quan_textView = findViewById(R.id.snap_quan);
        nutri_imageView = findViewById(R.id.snap_nutri);

        intent = getIntent();
        Bundle extras = intent.getExtras();
        String foodName = extras.getString("productName_key");
        String ingredient = extras.getString("ingredient_key");
        String quantity = extras.getString("quantity_key");
        String imgurl = extras.getString("imgurl_key");

        foodname_textView.setText(foodName);
        ingred_textView.setText(ingredient);
        quan_textView.setText(quantity);
        Picasso.get().load(imgurl).into(nutri_imageView);

    }
}
