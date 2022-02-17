package com.example.fyp_app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FoodNutrient extends AppCompatActivity {
    JSONArray sortedJsonArray = new JSONArray();
    FoodNutrientAdapter adapter;
    RecyclerView recyclerView;
    TextView nutri;
    TextView nutri_descri;

    private String Nutrition = "Vitamin C";
    private String NutritionDescri = " Vitamins are organic compounds that\n peopleneed in small quantities.\n Most vitamins need to come from food.";
    private String url = "https://api.spoonacular.com/recipes/findByNutrients?minVitaminC=10&number=10&random=true&apiKey=7ed522da19374c0f8aba43afd5c3aaba";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient);
        recyclerView = findViewById(R.id.recyclerView);
        nutri = findViewById(R.id.nutri);
        nutri.setText(Nutrition);
        nutri_descri = findViewById(R.id.nutri_descri);
        nutri_descri.setText(NutritionDescri);
        new FetchDataTask().execute(url);
    }

    private class FetchDataTask extends AsyncTask<String, Integer, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String line;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                JSONArray jsonArray = new JSONArray(sb.toString());
                ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonValues.add(jsonArray.getJSONObject(i));
                }

                Collections.sort(jsonValues, new Comparator<JSONObject>() {
                    // You can change "Name" with "ID" if you want to sort by ID
                    private static final String KEY_NAME = "vitaminC";

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try {
                            valA = ((String) a.get(KEY_NAME)).replaceAll("mg", "");
                            valB = ((String) b.get(KEY_NAME)).replaceAll("mg", "");
                        } catch (JSONException e) {

                        }
                        Integer A = Integer.parseInt(valA);
                        Integer B = Integer.parseInt(valB);
                        return -A.compareTo(B);
                    }
                });

                for (int i = 0; i < jsonArray.length(); i++) {
                    sortedJsonArray.put(jsonValues.get(i));
                }

                return sortedJsonArray;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress)
        {

        }

        @Override
        protected void onPostExecute(JSONArray dataFetched) {
            FoodNutrientAdapter adapter = new FoodNutrientAdapter(dataFetched);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        }
    }
}