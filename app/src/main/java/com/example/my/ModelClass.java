package com.example.my;

public class ModelClass {
    int photo;
    String name,details;

    public ModelClass(int photo, String name, String details) {
        this.photo = photo;
        this.name = name;
        this.details = details;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
