package com.example.medicinereminderproject;

//package com.gtappdevelopers.kotlingfgproject;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;


// Main Class
public class AlarmSetPage extends AppCompatActivity {
    AlarmDatabaseHelper databaseHelper;


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
    private EditText medicineInput;



    // Function that opens the TimePickup page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);


        // Putting the buttons and text from the XML file into a variables
        pickTimeBtn = findViewById(R.id.idBtnPickTime);
        saveBtn = findViewById(R.id.savebutton);
        selectedTimeTV = findViewById(R.id.idTVSelectedTime);

        // Medicine Name EditText
        medicineInput = findViewById(R.id.nameinput);

        // Check Box
        sundayBox = findViewById(R.id.sundayselection);
        mondayBox = findViewById(R.id.mondayselection);
        tuesdayBox = findViewById(R.id.tuesdayselection);
        wednesdayBox = findViewById(R.id.wednesdayselection);
        thursdayBox = findViewById(R.id.thursdayselection);
        fridayBox = findViewById(R.id.fridayselection);
        saturdayBox = findViewById(R.id.saturdayselection);
        sunday = findViewById(R.id.sundaytext);

        databaseHelper = new AlarmDatabaseHelper(this);


        String email = getIntent().getStringExtra("keyemail");

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

        // When Save Button is Clicked, run Check Function and add them into database
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean medicineEntered = false;
                boolean timeSelected = false;
                boolean dateSelected = false;

                // Run Check function
                Check();

                // Time
                // If the time hasn't been selected, show toast
                Log.d("Time selected", "" + timeset);
                if (timeset == null) {
                    Toast.makeText(AlarmSetPage.this, "Please set the time for the Alarm", Toast.LENGTH_SHORT).show();
                }
                else {
                    timeSelected = true;
                }

                // Date
                // Process to remove [] from Array, and set it to string
                String dayString = dayList.toString();
                String repeatDay = dayString.replaceAll("\\[","").replaceAll("\\]","");

                // If the day hasn't been selected, show toast
                if (dayString.isEmpty()) {
                    Toast.makeText(AlarmSetPage.this, "Please select the repeat date", Toast.LENGTH_SHORT).show();
                }
                else {
                    dateSelected = true;
                }

                // Medicine Name
                String medicineName = medicineInput.getText().toString();
                if (medicineName.isEmpty()) {
                    Toast.makeText(AlarmSetPage.this, "Please Enter the name of the Medicine", Toast.LENGTH_SHORT).show();
                }
                else {
                    medicineEntered = true;
                }

                // If three variables are all filled in then add them to database
                if (medicineEntered == true && timeSelected == true && dateSelected == true) {
                    // Write Date
                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String writeDate = date.format(Calendar.getInstance().getTime());

                    Boolean insert = databaseHelper.insertAlarm(email, timeset, repeatDay, medicineName, writeDate);

                    if (insert) {
                        Toast.makeText(AlarmSetPage.this, "Alarm Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AlarmPage.class);
                        intent.putExtra("keyemail", email);
                        startActivity(intent);
                    }


                    // Log to test
                    Log.d("Database List", email + ", " + timeset + ", " + repeatDay + ", " + medicineName +", " + writeDate);
                }


            }
        });




    }

    public void Check() {
        dayList.clear();
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

