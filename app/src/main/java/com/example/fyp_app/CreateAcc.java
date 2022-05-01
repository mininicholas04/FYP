package com.example.fyp_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp_app.ui.login.LoginActivity;

public class CreateAcc extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner body_part_spinner;
    private EditText name_text,email_text,pw_text;
    private String user_name,user_email,user_password,gender="male";
    private ImageButton btn_male,btn_female;
    private boolean isPressed = true;
    //private ListView genderList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_acc);

        body_part_spinner = findViewById(R.id.body_part_spinner);
        body_part_spinner.setOnItemSelectedListener(this);
        name_text = (EditText)findViewById(R.id.user_name);
        email_text = (EditText)findViewById(R.id.user_email);
        pw_text = (EditText)findViewById(R.id.user_password);
        btn_male = findViewById(R.id.btn_male);
        btn_female = findViewById(R.id.btn_female);
        Button btn_signup = (Button) findViewById(R.id.btn_signup);
        TextView btn_gotoLogin = (TextView)findViewById(R.id.btn_gotoLogin);

        String[] bodyPart = getResources().getStringArray(R.array.body_part);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, bodyPart);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_part_spinner.setAdapter(adapter);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = name_text.getText().toString();
                user_email = email_text.getText().toString();
                user_password = pw_text.getText().toString();
                boolean chk= checkDataEntered();
                if (chk){
                    //next page
                }
            }
        });
        btn_gotoLogin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                return false;
            }
        });
        //Gender button
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean checkDataEntered() {
        boolean check = false;
        //TextViews
        if(isEmail(email_text) && !isEmpty(name_text) && !isEmpty(pw_text)){
            check=true;
        }else{
            if (!isEmail(email_text)) {
                email_text.setError("Enter valid Email!");
            }
            if (isEmpty(name_text)) {
                name_text.setError("User name is required!");
            }
            if (isEmpty(pw_text)) {
                pw_text.setError("User password is required!");
            }
        }
        return check;
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

    public void signIn(View view) {
        Intent intent = new Intent(CreateAcc.this, LoginActivity.class);
        startActivity(intent);
    }

    public void setGenderButton(View view) {
        if (view.equals(btn_male)){
            btn_male.setBackgroundResource(R.drawable.btn_circle_selected);
            btn_female.setBackgroundResource(R.drawable.btn_circle);
        }else{
            btn_female.setBackgroundResource(R.drawable.btn_circle_selected);
            btn_male.setBackgroundResource(R.drawable.btn_circle);
        }
    }
}
