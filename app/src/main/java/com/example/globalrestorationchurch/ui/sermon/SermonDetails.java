package com.example.globalrestorationchurch.ui.sermon;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SermonDetails implements Parcelable {
    public String id;
    public String title;
    public String description;
    public String thumbnailUrl;
    public String published;

    public static final String KEY = "VIDEO";
    private static final String YOUTUBELINK = "https://www.youtube.com/watch?v=";

    private static final DateFormat INPUTFORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    private static final DateFormat CONVERTFORMATTER = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

    public SermonDetails(String id, String title, String description, String thumbnail, String published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnail;
        try {
            Date date = INPUTFORMATTER.parse(published);
            assert date != null;
            this.published = CONVERTFORMATTER.format(date);
        } catch (ParseException e) {
            Log.e("tag", e.toString() );
        }
    }

    String getPath() {
        return YOUTUBELINK + id;
    }

    protected SermonDetails(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        thumbnailUrl = in.readString();
        published = in.readString();
    }

    public static final Creator<SermonDetails> CREATOR = new Creator<SermonDetails>() {
        @Override
        public SermonDetails createFromParcel(Parcel in) {
            return new SermonDetails(in);
        }

        @Override
        public SermonDetails[] newArray(int size) {
            return new SermonDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(published);
    }
}
