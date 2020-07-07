package com.example.globalrestorationchurch.ui.sermon;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SermonDetails {
    public String id;
    public String title;
    public String description;
    public String thumbnailUrl;
    public String published;

    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    DateFormat convertFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

    public SermonDetails(String id, String title, String description, String thumbnail, String published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnail;
        try {
            Date date = inputFormatter.parse(published);
            assert date != null;
            this.published = convertFormat.format(date);
        } catch (ParseException e) {
            Log.e("tag", e.toString() );
        }
    }
}
