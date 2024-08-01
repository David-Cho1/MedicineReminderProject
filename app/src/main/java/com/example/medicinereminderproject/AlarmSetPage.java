package com.example.medicinereminderproject;

//package com.gtappdevelopers.kotlingfgproject;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;


// Main Class
public class AlarmSetPage extends AppCompatActivity {
    // TIME PICKING

    // Creating Variable
    private Button pickTimeBtn;
    private Button saveBtn;
    private TextView selectedTimeTV;
    private CheckBox sundayBox;
    private CheckBox mondayBox;
    private CheckBox tuesdayBox;
    private CheckBox wednesdayBox;
    private CheckBox thursdayBox;
    private CheckBox fridayBox;
    private CheckBox saturdayBox;
    private TextView sunday;
    private String timeset;
    private ArrayList dayList;
    private String user;
    private String email;


    // Function that opens the TimePickup page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);

        // Putting the buttons and text from the XML file into a variables
        pickTimeBtn = findViewById(R.id.idBtnPickTime);
        saveBtn = findViewById(R.id.savebutton);
        selectedTimeTV = findViewById(R.id.idTVSelectedTime);

        // Check Box
        sundayBox = findViewById(R.id.sundayselection);
        mondayBox = findViewById(R.id.mondayselection);
        tuesdayBox = findViewById(R.id.tuesdayselection);
        wednesdayBox = findViewById(R.id.wednesdayselection);
        thursdayBox = findViewById(R.id.thursdayselection);
        fridayBox = findViewById(R.id.fridayselection);
        saturdayBox = findViewById(R.id.saturdayselection);
        sunday = findViewById(R.id.sundaytext);

        // Selected Day List
        dayList = new ArrayList<String>();

        // Listen when the button is clicked
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            // Function that fixes glitch where it preceding 0
            public String checkDigit(int number) {
                // if the number is smaller than 9 then add "0" at the front
                return number <= 9 ? "0" + number : String.valueOf(number);
            }

            // Function that gets the Time from the Calendar
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                // Set integer variables to the separated time parts
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmSetPage.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            // Function that changes the TextView to the Setted time
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedTimeTV.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute));
                                // set it to a variable
                                timeset = (checkDigit(hourOfDay) + ":" + checkDigit(minute));
                                Log.d("Time Set to", "" + timeset);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        // Save Button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();

                Log.d("Database List", "" + dayList);

            }
        });




    }

    public void Check() {
        // Check which day box is selected, and if it is checked add them to list
        if (sundayBox.isChecked())
            dayList.add("sun");

        if (mondayBox.isChecked())
            dayList.add("mon");

        if (tuesdayBox.isChecked())
            dayList.add("tue");

        if (wednesdayBox.isChecked())
            dayList.add("wed");

        if (thursdayBox.isChecked())
            dayList.add("thu");

        if (fridayBox.isChecked())
            dayList.add("fri");

        if (saturdayBox.isChecked())
            dayList.add("sat");

    }
}

