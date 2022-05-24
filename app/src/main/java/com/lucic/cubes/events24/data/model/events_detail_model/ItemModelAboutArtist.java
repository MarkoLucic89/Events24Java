package com.lucic.cubes.events24.data.model.events_detail_model;

public class ItemModelAboutArtist extends EventsDetailItemModel {

    public String imageUrl;
    public String description;

    public ItemModelAboutArtist() {
    }

    public ItemModelAboutArtist(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_ABOUT_ARTIST;
    }
}
