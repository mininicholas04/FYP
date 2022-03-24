package com.example.fyp_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ScanFood extends AppCompatActivity {

    private TextView foodname_textView;
    //private TextView ingred_textView;
    private TextView quan_textView;
    //private ImageView nutri_imageView;
    /*private TextView fat_textview;
    private TextView safat_textview;
    private TextView sugar_textview;
    private TextView salt_textview;*/
    private RequestQueue mQueue;
    private RelativeLayout fat_layout;
    private RelativeLayout safat_layout;
    private RelativeLayout sugar_layout;
    private RelativeLayout salt_layout;
    private TextView fat_num;
    private TextView safat_num;
    private TextView sugar_num;
    private TextView salt_num;
    private TextView percent_fat;
    private TextView percent_safat;
    private TextView percent_sugar;
    private TextView percent_salt;
    private ImageView nutri_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snap_details);
        foodname_textView = findViewById(R.id.snap_food);
        //ingred_textView = findViewById(R.id.snap_ingred);
        quan_textView = findViewById(R.id.snap_quan);
        //nutri_imageView = findViewById(R.id.snap_nutri);
       /* fat_textview = findViewById(R.id.snap_fat);
        safat_textview = findViewById(R.id.snap_safat);
        sugar_textview = findViewById(R.id.snap_sugar);
        salt_textview = findViewById(R.id.snap_salt);*/
        fat_layout = findViewById(R.id.fat_layout);
        safat_layout = findViewById(R.id.safat_layout);
        sugar_layout = findViewById(R.id.sugar_layout);
        salt_layout = findViewById(R.id.salt_layout);
        fat_num = findViewById(R.id.fat_num);
        safat_num = findViewById(R.id.safat_num);
        sugar_num = findViewById(R.id.sugar_num);
        salt_num = findViewById(R.id.salt_num);
        percent_fat = findViewById(R.id.percent_fat);
        percent_safat = findViewById(R.id.percent_safat);
        percent_sugar = findViewById(R.id.percent_sugar);
        percent_salt = findViewById(R.id.percent_salt);
        nutri_score = findViewById(R.id.nutri_score);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        mQueue = Volley.newRequestQueue(this);
    }

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public void ScanButton(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult!=null){
            if(intentResult.getContents()==null){
                foodname_textView.setText("Not found");
                quan_textView.setText("Not found");

            }
            else{
                jsonParse(intentResult.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void jsonParse(String numeroean){
        String url = "https://world.openfoodfacts.org/api/v0/product/"+numeroean+".json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject productname = response.getJSONObject("product");
                    String productnameString = productname.getString("product_name");
                    //String ingredientsString = productname.getString("ingredients_text");
                    String quantityString = productname.getString("quantity");
                    //String imgurl = productname.getString("image_nutrition_url");

                    JSONObject ingredObject = productname.getJSONObject("nutrient_levels");
                    String fatString = ingredObject.getString("fat");
                    String safatString = ingredObject.getString("saturated-fat");
                    String sugarString = ingredObject.getString("sugars");
                    String saltString = ingredObject.getString("salt");

                    JSONObject nutrimentsObject = productname.getJSONObject("nutriments");
                    String fatNumString = nutrimentsObject.getString("fat_serving");
                    String safatNumString = nutrimentsObject.getString("saturated-fat_serving");
                    String sugarNumString = nutrimentsObject.getString("sugars_serving");
                    String saltNumString = nutrimentsObject.getString("salt_serving");

                    String imgurl_score = productname.getString("nutriscore_grade");

                    foodname_textView.setText(productnameString);
                    //ingred_textView.setText(ingredientsString);
                    quan_textView.setText(quantityString);
                    //Picasso.get().load(imgurl).into(nutri_imageView);
                    /*fat_textview.setText(fatString);
                    safat_textview.setText(safatString);
                    sugar_textview.setText(sugarString);
                    salt_textview.setText(saltString);*/
                    fat_num.setText(fatNumString);
                    safat_num.setText(safatNumString);
                    sugar_num.setText(sugarNumString);
                    salt_num.setText(saltNumString);

                    Float fat_percentage = Float.parseFloat(fatNumString);
                    fat_percentage = (fat_percentage/70)*100;
                    percent_fat.setText(df.format(fat_percentage) + " %");

                    Float safat_percentage = Float.parseFloat(safatNumString);
                    safat_percentage = (safat_percentage/70)*100;
                    percent_safat.setText(df.format(safat_percentage) + " %");

                    Float sugar_percentage = Float.parseFloat(sugarNumString);
                    sugar_percentage = (sugar_percentage/70)*100;
                    percent_sugar.setText(df.format(sugar_percentage) + " %");

                    Float salt_percentage = Float.parseFloat(saltNumString);
                    salt_percentage = (salt_percentage/70)*100;
                    percent_salt.setText(df.format(salt_percentage) + " %");


                    if (fatString.equals("high")){
                        fat_layout.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    if (safatString.equals("high")){
                        safat_layout.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    if (sugarString.equals("high")){
                        sugar_layout.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    if (saltString.equals("high")){
                        salt_layout.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    if (fatString.equals("moderate")){
                        fat_layout.setBackgroundColor(getResources().getColor(R.color.orange));
                    }

                    if (safatString.equals("moderate")){
                        safat_layout.setBackgroundColor(getResources().getColor(R.color.orange));
                    }

                    if (sugarString.equals("moderate")){
                        sugar_layout.setBackgroundColor(getResources().getColor(R.color.orange));
                    }

                    if (saltString.equals("moderate")){
                        salt_layout.setBackgroundColor(getResources().getColor(R.color.orange));
                    }

                    switch (imgurl_score){
                        case "a":
                            Picasso.get().load(R.drawable.level_a).into(nutri_score);
                            break;
                        case "b":
                            Picasso.get().load(R.drawable.level_b).into(nutri_score);
                            break;
                        case "c":
                            Picasso.get().load(R.drawable.level_c).into(nutri_score);
                            break;
                        case "d":
                            Picasso.get().load(R.drawable.level_d).into(nutri_score);
                            break;
                        case "e":
                            Picasso.get().load(R.drawable.level_e).into(nutri_score);
                            break;
                    }

                }
                catch(Exception e){
                    foodname_textView.setText("error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }
}