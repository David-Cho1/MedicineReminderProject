package com.example.medicinereminderproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


    public CustomAdapter(ArrayList<AlarmItem> alarmItems, Context mContext, AlarmDatabaseHelper alarmDatabaseHelper) {
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
        // run a check list
        checkDates(getDayList);
        // Change colour for selected day
        if (sunday) {
            holder.tv_sunText.setTextColor(R.color.red);
        }
        if (monday) {
            holder.tv_monText.setTextColor(R.color.red);
        }
        if (tuesday) {
            holder.tv_tueText.setTextColor(R.color.red);
        }
        if (wednesday) {
            holder.tv_wedText.setTextColor(R.color.red);
        }
        if (thursday) {
            holder.tv_thuText.setTextColor(R.color.red);
        }
        if (friday) {
            holder.tv_friText.setTextColor(R.color.red);
        }
        if (saturday) {
            holder.tv_satText.setTextColor(R.color.red);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int curPos = getAdapterPosition(); // Get Clicked Item list position
                    AlarmItem alarmItem = alarmItems.get(curPos);
                }
            });
        }
    }
}
