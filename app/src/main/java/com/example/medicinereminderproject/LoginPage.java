package com.example.medicinereminderproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class LoginPage extends AppCompatActivity {

    public String user;

    // Importing Classes
    AccDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Run main Function
        setInit();
    }

    // Main Function
    private void setInit() {
        // Making intense for Database Helper
        databaseHelper = new AccDatabaseHelper(this);

        // Buttons
        Button loginButton = findViewById(R.id.editButton);
        TextView relocateSign = findViewById(R.id.SignUpTextButton);

        // Edit textview
        EditText emailBox = findViewById(R.id.editTextEmailAddress);
        EditText passwordBox = findViewById(R.id.editTextPassword);

        // Error messages
        TextView errorEmail = findViewById(R.id.tvErrorEmail);
        TextView errorPassword = findViewById(R.id.tvErrorPassword);
        TextView errorOr = findViewById(R.id.tvErrorOr);

        // Login Button Click Listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = emailBox.getText().toString();
                String password = passwordBox.getText().toString();
                Boolean emailValid = false;
                Boolean passwordValid = false;

                // If the email is left blank, show error
                if (email.isEmpty()) {
                    errorOr.setText("");
                    errorEmail.setText("* Please enter email address");
                } else {
                    // Otherwise set email Valid
                    errorEmail.setText("");
                    emailValid = true;
                }

                // If the password is left blank, show error
                if (password.isEmpty()) {
                    errorOr.setText("");
                    errorPassword.setText("* Please enter your password");
                } else {
                    // Otherwise set password Valid
                    errorPassword.setText("");
                    passwordValid = true;
                }

                // If emailValid and PasswordValid values are true, Login user
                if (emailValid && passwordValid) {
                    errorOr.setText("");
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    // If check email and password is true
                    if (checkCredentials) {
                        Toast.makeText(LoginPage.this, "Login Successfully",
                                        Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        intent.putExtra("keyemail", email);

                        user = email;
                        Log.d("user", "" + user);
                        startActivity(intent);
                    } else {
                        errorEmail.setText("* Invalid Email Address");
                        errorOr.setText("or");
                        errorPassword.setText("* Incorrect Password");
                    }
                }
            }
        });

        // Button send to signup page
        relocateSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);
            }
        });
    }
}

