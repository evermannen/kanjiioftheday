package com.eversoft.kanjiioftheday;

public class KanjiInfo {
    private String date;
    private String title;
    private String description;

    private String filename;

    public KanjiInfo(String date, String title,
                     String description, String filename) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.filename = filename;
    }

    // Getters
    public String getDate() { return date; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getFilename() { return filename; }
}
