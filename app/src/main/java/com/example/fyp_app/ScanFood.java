package com.example.fyp_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;

public class ScanFood extends AppCompatActivity {

    private TextView foodname_textView;
    private TextView ingred_textView;
    private TextView quan_textView;
    private ImageView nutri_imageView;
    private TextView fat_textview;
    private TextView safat_textview;
    private TextView sugar_textview;
    private TextView salt_textview;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snap_details);
        foodname_textView = findViewById(R.id.snap_food);
        //ingred_textView = findViewById(R.id.snap_ingred);
        quan_textView = findViewById(R.id.snap_quan);
        nutri_imageView = findViewById(R.id.snap_nutri);
        fat_textview = findViewById(R.id.snap_fat);
        safat_textview = findViewById(R.id.snap_safat);
        sugar_textview = findViewById(R.id.snap_sugar);
        salt_textview = findViewById(R.id.snap_salt);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        mQueue = Volley.newRequestQueue(this);
    }

    public void ScanButton(View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.addExtra("SCAN_CAMERA_ID", 0);

        intentIntegrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult!=null){
            if(intentResult.getContents()==null){
                foodname_textView.setText("Not found");
                quan_textView.setText("Not found");
                ingred_textView.setText("Not found");
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
                    String imgurl = productname.getString("image_nutrition_url");

                    JSONObject ingredObject = productname.getJSONObject("nutrient_levels");
                    String fatString = ingredObject.getString("fat");
                    String safatString = ingredObject.getString("saturated-fat");
                    String sugarString = ingredObject.getString("sugars");
                    String saltString = ingredObject.getString("salt");

                    foodname_textView.setText(productnameString);
                    //ingred_textView.setText(ingredientsString);
                    quan_textView.setText(quantityString);
                    Picasso.get().load(imgurl).into(nutri_imageView);

                    if (fatString.equals("high")){
                        fat_textview.setTextColor(Color.RED);
                    }
                    if (safatString.equals("high")){
                        safat_textview.setTextColor(Color.RED);
                    }
                    if (sugarString.equals("high")){
                        sugar_textview.setTextColor(Color.RED);
                    }
                    if (saltString.equals("high")){
                        salt_textview.setTextColor(Color.RED);
                    }

                    fat_textview.setText(fatString);
                    safat_textview.setText(safatString);
                    sugar_textview.setText(sugarString);
                    salt_textview.setText(saltString);

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