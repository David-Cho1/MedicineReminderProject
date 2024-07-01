package com.example.medicinereminderproject;

//package com.gtappdevelopers.kotlingfgproject;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;


// Main Class
public class AlarmSetPage extends AppCompatActivity {






    // TIME PICKING


    // Creating Variable
    private Button pickTimeBtn;
    private TextView selectedTimeTV;


    // Function that opens the TimePickup page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmset);






        // Putting the buttons and text from the XML file into a variables
        pickTimeBtn = findViewById(R.id.idBtnPickTime);
        selectedTimeTV = findViewById(R.id.idTVSelectedTime);


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
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                selectedTimeTV.setText(checkDigit(hourOfDay) + ":"
                                        + checkDigit(minute));
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }






}

