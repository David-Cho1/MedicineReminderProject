package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class SignupPage extends AppCompatActivity {

    // Importing Classes
    AccDatabaseHelper databaseHelper;



    // Main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // onCreate GUI
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Making intense for Database Helper
        databaseHelper = new AccDatabaseHelper(this);

        // Buttons
        Button signButton = findViewById(R.id.signUpButton);

        // Edit textview
        EditText emailBox = findViewById(R.id.editTextEmailAddress);
        EditText passwordBox = findViewById(R.id.editTextPassword);
        EditText confirmBox = findViewById(R.id.editTextTextPasswordConfirm);

        // Error messages
        TextView errorEmail = findViewById(R.id.tvErrorEmail);
        TextView errorPassword = findViewById(R.id.tvErrorPassword);
        TextView errorConfirm = findViewById(R.id.tvErrorConfirmPassword);
        TextView relocateLogin = findViewById(R.id.LoginTextButton);

        // Listen to the button to clicked
        signButton.setOnClickListener(new View.OnClickListener() {

            // When the Signup button is clicked
            @Override
            public void onClick(View v) {
                // Get the text to string from editTextView
                String email = emailBox.getText().toString();
                String password = passwordBox.getText().toString();
                String confirmPasword = confirmBox.getText().toString();

                // Setting up the variables
                char characterAt = '@';     //
                char characterDot = '.';
                int numberOfDot = 0;
                int numberOfAt = 0;
                int passwordLen = password.length();
                boolean emailValid = false;
                boolean passwordValid = false;
                boolean confirmValid = false;
                boolean checkEmailExist = databaseHelper.checkEmail(email);

                // If the Email doesn't have the right format
                for (int i = 0; i < email.length(); i++) {
                    if (email.charAt(i) == characterAt) {
                        numberOfAt++;
                    }
                    if (email.charAt(i) == characterDot) {
                        numberOfDot++;
                    }
                }

                // Email Check
                // If the Edit Text Box it left blank, show error
                if (email.isEmpty()) {
                    errorEmail.setText("* Please enter email address");
                }

                // If email doesn't contain @ and . show invalid format error
                else if (numberOfAt != 1 && numberOfDot < 1) {
                    errorEmail.setText("* Invalid email format");
                }

                // If email already exists in database, show error
                else if (checkEmailExist == true) {
                    errorEmail.setText("* Email already Exist");
                }

                // If email doens't exist
                else if (checkEmailExist == false) {
                    // If email does contain @ and . Set the email valid
                    if (numberOfAt == 1 && numberOfDot >= 1) {
                        emailValid = true;
                    }
                    else {
                        emailValid = false;
                    }
                }

                // Password Check
                // If Password is left blank show error
                if (password.isEmpty()) {
                    errorPassword.setText("* Please enter your password");
                }
                // If password length is shorter than 8 show error
                else if (passwordLen < 8) {
                    errorPassword.setText("* Password must be longer than 8");
                }
                // Otherwise set password Valid
                else {
                    passwordValid = true;
                }

                // Confirm Check
                // If confirm is left blank show error
                if (confirmPasword.isEmpty()) {
                    errorConfirm.setText("* Please confirm password");
                }
                else if (!confirmPasword.equals(password)){
                    errorConfirm.setText("* Password doesn't match");
                }
                // Set password Confirm
                else {
                    confirmValid = true;
                }

                // If they are all valid set text back to normal
                if (emailValid == true) {
                    errorEmail.setText("");
                }
                // If password is correct, set error message back to normal
                if (passwordValid == true) {
                    errorPassword.setText("");
                }
                if (confirmValid == true) {
                    errorConfirm.setText("");
                }

                // If they are all valid, insert the data to the database
                if (emailValid == true && passwordValid == true && confirmValid == true) {
                    Boolean checkUserEmail = databaseHelper.checkEmail((email));

                    // If email Already Exist
                    if (checkUserEmail == false) {
                        Boolean insert = databaseHelper.insertData(email, password);

                        // If inserted successfully, show the toast and send to login page
                        if (insert == true) {
                            Toast.makeText(SignupPage.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                            startActivity(intent);
                        }
                        // If Insert failed, show signup failed
                        else {
                            Toast.makeText(SignupPage.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // If checkUserEmail == false, show User already exist
                    else {
                        Toast.makeText(SignupPage.this, "User Already Exist, Please Login", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        // Button send to login page
        relocateLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Login Page
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

    }
}














