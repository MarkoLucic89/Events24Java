package com.lucic.cubes.events24.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Event implements Serializable {

    public String title;
    public String date;
    public String time;
    public String type;
    public String imageBig;
    public String imageSmall;
    public String location;
    public String author;
    public long viewCount;
    public ArrayList<Ticket> tickets;
    public ArrayList<String> pictures;
    public AboutArtist aboutArtist;
    public ArrayList<News> newsList;

    public Event() {
        this.tickets = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.newsList = new ArrayList<>();
    }

    public Event(Map<String, Object> map) {

        this.tickets = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.newsList = new ArrayList<>();

        if (map.containsKey("title")) {
            this.title = (String) map.get("title");
        } else {
            this.title = "";
        }

        if (map.containsKey("date")) {
            this.date = (String) map.get("date");
        } else {
            this.date = "";
        }

        if (map.containsKey("time")) {
            this.time = (String) map.get("time");
        } else {
            this.time = "";
        }

        if (map.containsKey("type")) {
            this.type = (String) map.get("type");
        } else {
            this.type = "";
        }

        if (map.containsKey("imageBig")) {
            this.imageBig = (String) map.get("imageBig");
        } else {
            this.imageBig = "";
        }

        if (map.containsKey("imageSmall")) {
            this.imageSmall = (String) map.get("imageSmall");
        } else {
            this.imageSmall = "";
        }

        if (map.containsKey("location")) {
            this.location = (String) map.get("location");
        } else {
            this.location = "";
        }

        if (map.containsKey("author")) {
            this.author = (String) map.get("author");
        } else {
            this.author = "";
        }

        if (map.containsKey("viewCount")) {
            this.viewCount = (long) map.get("viewCount");
        } else {
            this.viewCount = 0;
        }

        if (map.containsKey("tickets")) {

            ArrayList<Map<String, String>> maps = (ArrayList<Map<String, String>>) map.get("tickets");

            for (Map<String, String> mapTicket : maps) {
                Ticket ticket = new Ticket(mapTicket);
                this.tickets.add(ticket);
            }

        } else {

        }

        if (map.containsKey("pictures")) {
            this.pictures = (ArrayList<String>) map.get("pictures");
        }

        if (map.containsKey("aboutArtist")) {
            this.aboutArtist = new AboutArtist((Map<String, String>) map.get("aboutArtist"));
        } else {
            this.aboutArtist = new AboutArtist();
        }

        if (map.containsKey("news")) {

            ArrayList<Map<String, String>> maps = (ArrayList<Map<String, String>>) map.get("news");

            for (Map<String, String> mapNews : maps) {
                News news = new News(mapNews);
                this.newsList.add(news);
            }
        }


    }
}
