package com.lucic.cubes.events24.data.model;

import java.io.Serializable;
import java.util.Map;

public class Ticket implements Serializable {
    public String title;
    public String price;


    public Ticket(Map<String, String> map) {

        if (map.containsKey("title")) {
            this.title = (String) map.get("title");
        } else {
            this.title = "";
        }

        if (map.containsKey("price")) {
            this.price = (String) map.get("price");
        } else {
            this.price = "";
        }
    }
}
