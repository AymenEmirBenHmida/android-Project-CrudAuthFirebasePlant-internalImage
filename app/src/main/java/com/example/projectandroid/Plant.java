package com.example.projectandroid;

public class Plant {
    String name;
    String description;
    String url;

    public Plant(String name, String description,String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }
    public Plant(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Plant() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
