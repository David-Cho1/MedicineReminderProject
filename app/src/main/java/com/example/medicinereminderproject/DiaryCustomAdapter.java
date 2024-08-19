package com.example.medicinereminderproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Connection for Recycler View
public class DiaryCustomAdapter extends RecyclerView.Adapter<DiaryCustomAdapter.ViewHolder> {
    private ArrayList<DiaryItem> diaryItems;
    private String mUser;
    private Context mContext;
    private DiaryDatabaseHelper mDBHelper;


    public DiaryCustomAdapter(String mUser, ArrayList<DiaryItem> diaryItems, Context mContext) {
        this.mUser = mUser;
        this.diaryItems = diaryItems;
        this.mContext = mContext;
        mDBHelper = new DiaryDatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Connecting from here
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(diaryItems.get(position).getTitle());
        holder.tv_date.setText(diaryItems.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return diaryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private String title;
        private String date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.diaryTitle);
            tv_date = itemView.findViewById(R.id.timeText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition(); // Get Clicked Item list position
                    DiaryItem diaryItem = diaryItems.get(curPos);


                    String[] StrChoiceItems = {"Delete"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Would You like to delete this Diary?");
                    builder.setItems(StrChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0) {
                                Log.d("email user", "" + mUser);

                                // Get the Variable from the selected block
                                title = diaryItems.get(position).getTitle();
                                date = diaryItems.get(position).getDate();



                                // Delete
                                diaryItems.remove(curPos);
                                notifyItemRemoved(curPos);


                                Boolean alarmDeleted = mDBHelper.deleteDiary(mUser, title, date);
                                Toast.makeText(mContext, "Diary Deleted", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                    builder.show();
                }
            });
        }
    }

    // Function called from activity, receive new contents and add into this adapter
    public void addItem(DiaryItem _item) {
        diaryItems.add(0, _item);
        notifyItemInserted(0);
    }
}