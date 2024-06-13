package com.example.medicinereminderproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class LoginPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setInit();

    }
    private void setInit() {
        // Listen to the Text View and when it's clicked, send to the SignUp Page
        TextView sendSignUpbtn = (TextView)findViewById(R.id.signUpText);
        sendSignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, SignupPage.class));
            }
        });
    }
}

