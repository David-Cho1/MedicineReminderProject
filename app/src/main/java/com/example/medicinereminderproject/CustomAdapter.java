package com.example.medicinereminderproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

// Connection for Recycler View
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<AlarmItem> alarmItems;
    private Context mContext;
    private AlarmDatabaseHelper alarmDatabaseHelper;
    private String getDayList;
    private Boolean sunday = false;
    private Boolean monday = false;
    private Boolean tuesday = false;
    private Boolean wednesday = false;
    private Boolean thursday = false;
    private Boolean friday = false;
    private Boolean saturday = false;
    private ArrayList<String> dayList = new ArrayList<>();
    private String writeDate;



    public CustomAdapter(ArrayList<AlarmItem> alarmItems, Context mContext) {
        this.alarmItems = alarmItems;
        this.mContext = mContext;
        alarmDatabaseHelper = new AlarmDatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Connecting from here
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list, parent, false);
        return new ViewHolder(holder);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        // Getting the Text from ArrayList, and change the gui text
        holder.tv_name.setText(alarmItems.get(position).getMed());
        holder.tv_time.setText(alarmItems.get(position).getTime());
        // See what days are selected, and turn the text red whatever is selected
        getDayList = alarmItems.get(position).getRepeat();

        dayList.clear(); // reset arraylist

        // run a check list
        checkDates(getDayList);
        // Change colour for selected day
        if (sunday) {
            Log.d("Sunday", "True");
            holder.tv_sunText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("sun");
        }
        if (monday) {
            holder.tv_monText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("mon");

        }
        if (tuesday) {
            holder.tv_tueText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("tue");
        }
        if (wednesday) {
            holder.tv_wedText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("wed");
        }
        if (thursday) {
            holder.tv_thuText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("thu");
        }
        if (friday) {
            holder.tv_friText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("fri");
        }
        if (saturday) {
            holder.tv_satText.setTextColor(Color.parseColor("#EB4034"));
            dayList.add("sat");
        }

    }

    @Override
    public int getItemCount() {
        return alarmItems.size();
    }

    public void checkDates(String days) {
        if (days.contains("sun")) {
            sunday = true;
        }
        if (days.contains("mon")) {
            monday = true;
        }
        if (days.contains("tue")) {
            tuesday = true;
        }
        if (days.contains("wed")) {
            wednesday = true;
        }
        if (days.contains("thu")) {
            thursday = true;
        }
        if (days.contains("fri")) {
            friday = true;
        }
        if (days.contains("sun")) {
            saturday = true;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_satText;
        private TextView tv_monText;
        private TextView tv_tueText;
        private TextView tv_wedText;
        private TextView tv_thuText;
        private TextView tv_friText;
        private TextView tv_sunText;
        private String med;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            tv_name = itemView.findViewById(R.id.medicineName);
            tv_time = itemView.findViewById(R.id.timeText);
            tv_satText = itemView.findViewById(R.id.satText);
            tv_monText = itemView.findViewById(R.id.monText);
            tv_tueText = itemView.findViewById(R.id.tueText);
            tv_wedText = itemView.findViewById(R.id.wedText);
            tv_thuText = itemView.findViewById(R.id.thuText);
            tv_friText = itemView.findViewById(R.id.friText);
            tv_sunText = itemView.findViewById(R.id.sunText);

            String textTime = tv_time.getText().toString();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition(); // Get Clicked Item list position
                    AlarmItem alarmItem = alarmItems.get(curPos);



                    String[] StrChoiceItems = {"Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Would You like to delete this alarm?");
                    builder.setItems(StrChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0) {
                                // Get email from Login Page
                                LoginPage getUser = new LoginPage();
                                String email = getUser.user;

                                Log.d("email user", "" + email);

                                med = alarmItems.get(position).getMed();

                                // Delete
                                alarmItems.remove(curPos);
                                notifyItemRemoved(curPos);
                                String dayString = dayList.toString();
                                String repeatDay = dayString.replaceAll("\\[","").replaceAll("\\]","");
                                Log.d("Delete Data", email + " " + textTime + " " + repeatDay + " " + med);
                                alarmDatabaseHelper.deleteAlarm(email, textTime, repeatDay, med);
                                Toast.makeText(mContext, "Alarm Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }


    }
    // Function called from activity, receive new contents and add into this adapter
    public void addItem(AlarmItem _item) {
        alarmItems.add(0, _item);
        notifyItemInserted(0);
    }
}
