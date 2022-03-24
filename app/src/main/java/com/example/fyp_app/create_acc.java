package com.example.fyp_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_app.ui.login.LoginActivity;

public class create_acc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner body_part_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc);

        body_part_spinner = findViewById(R.id.body_part_spinner);
        body_part_spinner.setOnItemSelectedListener(this);



        Button btn_signup = (Button) findViewById(R.id.btn_signup);

        String[] bodyPart = getResources().getStringArray(R.array.body_part);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, bodyPart);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_part_spinner.setAdapter(adapter);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name_text = (EditText)findViewById(R.id.user_name);
                String user_name = name_text.getText().toString();

                EditText email_text = (EditText)findViewById(R.id.user_email);
                String user_email = email_text.getText().toString();

                EditText pw_text = (EditText)findViewById(R.id.user_password);
                String user_password = pw_text.getText().toString();


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.body_part_spinner) {
            String valueFromSpinner = parent.getItemAtPosition(position).toString();
            //get spinner value
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
