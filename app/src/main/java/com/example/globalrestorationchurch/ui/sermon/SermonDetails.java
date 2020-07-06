package com.example.globalrestorationchurch.ui.sermon;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class SermonDetails {
    public String title;
    public String description;
    public Drawable thumbnail;
    public Date time;

    public SermonDetails(String title, String description, Drawable thumbnail, Date time) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.time = time;
    }

}
