package com.lucic.cubes.events24.data.model;

import java.util.Map;

public class Event {

    public String title;
    public String date;
    public String time;
    public String type;
    public String imageBig;
    public String imageSmall;
    public String location;
    public String author;
    public long viewCount;

    public Event() {

    }

    public Event(Map<String, Object> map) {

        if (map.containsKey("title")) {
            title = (String) map.get("title");
        } else {
            title = "";
        }

        if (map.containsKey("date")) {
            date = (String) map.get("date");
        } else {
            date = "";
        }

        if (map.containsKey("time")) {
            time = (String) map.get("time");
        } else {
            time = "";
        }

        if (map.containsKey("type")) {
            type = (String) map.get("type");
        } else {
            type = "";
        }

        if (map.containsKey("imageBig")) {
            imageBig = (String) map.get("imageBig");
        } else {
            imageBig = "";
        }

        if (map.containsKey("imageSmall")) {
            imageSmall = (String) map.get("imageSmall");
        } else {
            imageSmall = "";
        }

        if (map.containsKey("location")) {
            location = (String) map.get("location");
        } else {
            location = "";
        }

        if (map.containsKey("author")) {
            author = (String) map.get("author");
        } else {
            author = "";
        }

        if (map.containsKey("viewCount")) {
            viewCount = (long) map.get("viewCount");
        } else {
            viewCount = 0;
        }


    }
}
