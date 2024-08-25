package com.example.medicinereminderproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_page);

        // Progress bar
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setProgress(40);

        // Sleep 1000 millis
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Handler
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Set progress bar
                progressBar.setProgress(50);
                progressBar.setProgress(90);
                startActivity(new Intent(SplashPage.this, LoginPage.class));
                finish();
            }
        }, 4000);
    }
}