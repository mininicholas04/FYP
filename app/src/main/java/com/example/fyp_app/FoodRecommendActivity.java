package com.example.fyp_app;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fyp_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodRecommendActivity extends AppCompatActivity {

    // creating a variable for our array list, adapter class,
    // recycler view, progressbar, nested scroll view
    private ArrayList<FoodModal> userModalArrayList;
    private FoodRVAdapter userRVAdapter;
    private RecyclerView userRV;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;

    // creating a variable for our page and limit as 2
    // as our api is having highest limit as 2 so
    // we are setting a limit = 2
    //int page = 0, limit = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_recommend);

        // creating a new array list.
        userModalArrayList = new ArrayList<>();

        // initializing our views.
        userRV = findViewById(R.id.idRVUsers);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);

        // calling a method to load our api.
        getDataFromAPI();

        // adding on scroll change listener method for our nested scroll view.
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    //page++;
                    loadingPB.setVisibility(View.VISIBLE);
                    getDataFromAPI();
                }
            }
        });
    }

    private void getDataFromAPI() {
        /*if (page > limit) {
            // checking if the page number is greater than limit.
            // displaying toast message in this case when page>limit.
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();
            // hiding our progress bar.
            loadingPB.setVisibility(View.GONE);
            return;
        }*/
        // creating a string variable for url .
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=Salmon&minFat=10&minProtein=10&minCalories=50&minCarbs=10&number=4&apiKey=36e34d5dffd4425aa00f5b43c817935d&sort=random";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(FoodRecommendActivity.this);

        // creating a variable for our json object request and passing our url to it.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // on below line we are extracting data from our json array.
                    JSONArray dataArray = response.getJSONArray("results");

                    // passing data from our json array in our array list.
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);

                        // on below line we are extracting data from our json object.
                        userModalArrayList.add(new FoodModal(

                                jsonObject.getString("title"),

                                jsonObject.getString("image"),

                                jsonObject.getJSONObject("nutrition").getJSONArray("nutrients").getJSONObject(0).getInt("amount"),

                                jsonObject.getJSONObject("nutrition").getJSONArray("nutrients").getJSONObject(1).getInt("amount"),

                                jsonObject.getJSONObject("nutrition").getJSONArray("nutrients").getJSONObject(2).getInt("amount"),

                                jsonObject.getJSONObject("nutrition").getJSONArray("nutrients").getJSONObject(3).getInt("amount")
                        ));

                        // passing array list to our adapter class.
                        userRVAdapter = new FoodRVAdapter(userModalArrayList, FoodRecommendActivity.this);

                        // setting layout manager to our recycler view.
                        userRV.setLayoutManager(new LinearLayoutManager(FoodRecommendActivity.this));

                        // setting adapter to our recycler view.
                        userRV.setAdapter(userRVAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling on error listener method.
                Toast.makeText(FoodRecommendActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // calling a request queue method
        // and passing our json object
        queue.add(jsonObjectRequest);
    }
}