package com.lucic.cubes.events24.data.model;

import java.io.Serializable;
import java.util.Map;

public class News implements Serializable {

    public String imageUrl;
    public String date;
    public String title;
    public String description;

    public News() {
        this.imageUrl = "";
        this.date = "";
        this.title = "";
        this.description = "";
    }

    public News(String imageUrl, String date, String title, String description) {
        this.imageUrl = imageUrl;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    public News(Map<String, String> map) {

        if (map.containsKey("imageUrl")) {
            this.imageUrl = (String) map.get("imageUrl");
        } else {
            this.imageUrl = "";
        }

        if (map.containsKey("date")) {
            this.date = (String) map.get("date");
        } else {
            this.date = "";
        }

        if (map.containsKey("title")) {
            this.title = (String) map.get("title");
        } else {
            this.title = "";
        }

        if (map.containsKey("description")) {
            this.description = (String) map.get("description");
        } else {
            this.description = "";
        }

    }
}
