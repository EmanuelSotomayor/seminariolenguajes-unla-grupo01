package com.example.pelisapp;

public class FilmModel {
    int image;
    String title;
    String info;

    public FilmModel(int image, String title, String info) {
        this.image = image;
        this.title = title;
        this.info = info;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }
}
