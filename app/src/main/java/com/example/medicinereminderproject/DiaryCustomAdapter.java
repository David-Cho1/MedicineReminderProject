package com.example.medicinereminderproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private int mId;
    private String mUser;
    private Context mContext;
    private DiaryDatabaseHelper mDBHelper;

    // Constructor
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

    // Set the Text to data from database at position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(diaryItems.get(position).getTitle());
        holder.tv_date.setText(diaryItems.get(position).getDate());
    }

    // Count item Size
    @Override
    public int getItemCount() {
        return diaryItems.size();
    }

    // View Holder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_date;
        private String title;
        private String date;
        private DiaryViewPage viewPage;
        private int id;

        // Method where does a function when Item is clicked
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find GUI id and set them to variable
            tv_title = itemView.findViewById(R.id.diaryTitle);
            tv_date = itemView.findViewById(R.id.timeText);

            // When ItemView is clicked, sho Dialog
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition(); // Get Clicked Item list position
                    DiaryItem diaryItem = diaryItems.get(curPos);

                    // Button in dialog
                    String[] StrChoiceItems = {"View","Edit","Delete"};

                    // Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    // Set Dialog title
                    builder.setTitle("Would You like to delete this Diary?");
                    builder.setItems(StrChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            // View Button Clicked
                            if(position == 0) {
                                Log.d("position clicked", "" + position);
                                title = diaryItems.get(curPos).getTitle();
                                date = diaryItems.get(curPos).getDate();


                                // Send to diary View Page
                                Intent intent = new Intent(mContext, DiaryViewPage.class);

                                // Send significant resources to next activity
                                intent.putExtra("keyemail", mUser);
                                intent.putExtra("keytitle", title);
                                intent.putExtra("keydate", date);

                                // Start activity
                                mContext.startActivity(intent);
                            }

                            // Edit Button Clicked
                            else if(position == 1) {
                                Log.d("email user", "" + mUser);

                                // Get the Variable from the selected block
                                title = diaryItems.get(curPos).getTitle();
                                date = diaryItems.get(curPos).getDate();
                                id = diaryItems.get(curPos).getId();

                                Log.d("Log D diary", "" + title + "\n" + date +"\n" + id);

                                // Change ID to string value to process putExtra()
                                String strId = Integer.toString(id);

                                // Send to diary Edit Page
                                Intent intent = new Intent(mContext, DiaryEditPage.class);

                                // Send significant resources to next activity
                                intent.putExtra("keyemail", mUser);
                                intent.putExtra("keytitle", title);
                                intent.putExtra("keydate", date);
                                intent.putExtra("keyid", strId);

                                // Start an Activity
                                mContext.startActivity(intent);
                            }

                            // Delete Button Clicked
                            else if(position == 2) {
                                Log.d("email user", "" + mUser);

                                // Get the Variable from the selected block
                                title = diaryItems.get(curPos).getTitle();
                                date = diaryItems.get(curPos).getDate();

                                Log.d("Log D diary", "" + title + "\n" + date);

                                Boolean diaryDeleted = mDBHelper.deleteDiary(mUser, title, date);

                                Log.d("diaryDelete Boolean", "" + diaryDeleted);
                                // If deleted Successfully, show toast

                                Toast.makeText(mContext, "Diary Deleted", Toast.LENGTH_SHORT).show();


                                // Delete GUI
                                diaryItems.remove(curPos);
                                notifyItemRemoved(curPos);
                            }

                        }
                    });
                    // Show the builder
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