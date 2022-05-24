package com.lucic.cubes.events24.data.model;

import java.io.Serializable;
import java.util.Map;

public class AboutArtist implements Serializable {
    public String imageUrl;
    public String description;

    public AboutArtist() {
        this.imageUrl = "";
        this.description = "";
    }

    public AboutArtist(Map<String, String> map) {

        if (map.containsKey("imageUrl")) {
            this.imageUrl = (String) map.get("imageUrl");
        } else {
            this.imageUrl = "";
        }

        if (map.containsKey("description")) {
            this.description = (String) map.get("description");
        } else {
            this.description = "";
        }
    }
}
