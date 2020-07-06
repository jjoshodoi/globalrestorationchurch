package com.example.globalrestorationchurch.ui.sermon;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class SermonDetails {
    public String title;
    public String description;
    public String thumbnail;
    public String time;

    public SermonDetails(String title, String description, String thumbnail, String time) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.time = time;
    }
}
