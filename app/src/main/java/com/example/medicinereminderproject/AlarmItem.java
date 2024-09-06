package com.example.medicinereminderproject;

public class AlarmItem {
    private int id;
    private String user;
    private String time;
    private String repeat;
    private String med;

    // Constructor
    public AlarmItem() {

    }

    // Getter and Setter for variables
    // ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Email
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Time
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // Repeat days
    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    // Medicine Name
    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    // Write date
    public void setWriteDate(String writeDate) {
    }
}
