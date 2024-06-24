package com.example.medicinereminderproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class LoginPage extends AppCompatActivity {

    // Importing Classes
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setInit();

    }
    // Main Function
    private void setInit() {
        // Making intense for Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Buttons
        Button loginButton = findViewById(R.id.loginButton);

        // Edit textview
        EditText emailBox = findViewById(R.id.editTextEmailAddress);
        EditText passwordBox = findViewById(R.id.editTextPassword);

        // Error messages
        TextView errorEmail = findViewById(R.id.tvErrorEmail);
        TextView errorPassword = findViewById(R.id.tvErrorPassword);


        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = emailBox.getText().toString();
                String password = passwordBox.getText().toString();
                Boolean emailValid = false;
                Boolean passwordValid = false;

                // If the email is left blank, show error
                if (email.isEmpty()) {
                    errorEmail.setText("* Please enter your email address");
                }
                // Otherwise set email Valid
                else {
                    emailValid = true;
                }

                // If the password is left blank, show error
                if (password.isEmpty()) {
                    errorPassword.setText("* Please enter your password");
                }
                // Otherwise set password Valid
                else {
                    passwordValid = true;
                }

                if (emailValid == true && passwordValid == true) {
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
                }

            }
        });

        // Listen to the Text View and when it's clicked, send to the SignUp Page
        TextView sendSignUpbtn = (TextView)findViewById(R.id.SignUpTextButton);
        sendSignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, SignupPage.class));
            }
        });
    }
}

