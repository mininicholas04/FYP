package com.example.fyp_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fyp_app.ui.login.LoginActivity;

public class splashscreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Button login_button = (Button) findViewById(R.id.btn_login);

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(splashscreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
