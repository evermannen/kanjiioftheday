package com.eversoft.kanjiioftheday;

public class KanjiInfo {
    private String date;
    private String title;
    private String description;

    public KanjiInfo(String date, String title, String description) {
        this.date = date;
        this.title = title;
        this.description = description;
    }

    // Getters
    public String getDate() { return date; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
}
