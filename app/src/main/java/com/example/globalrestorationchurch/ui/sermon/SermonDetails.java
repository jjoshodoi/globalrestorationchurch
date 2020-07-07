package com.example.globalrestorationchurch.ui.sermon;

public class SermonDetails {
    public String id;
    public String title;
    public String description;
    public String thumbnailUrl;
    public String published;

    public SermonDetails(String id, String title, String description, String thumbnail, String published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnail;
        this.published = published;
    }
}
