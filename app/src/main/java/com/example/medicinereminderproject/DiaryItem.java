package com.example.medicinereminderproject;

public class DiaryItem {
    private int id;
    private String user;
    private String title;
    private String context;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Constructor
    public DiaryItem() {

    }

    // Getter and Setter
    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // User
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Context
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    // Write Date
    public void setWriteDate(String writeDate) {
    }
}
