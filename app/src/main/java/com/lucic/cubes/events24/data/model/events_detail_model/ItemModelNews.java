package com.lucic.cubes.events24.data.model.events_detail_model;


public class ItemModelNews extends EventsDetailItemModel {

    public String imageUrl;
    public String date;
    public String title;
    public String description;

    public ItemModelNews() {
    }

    public ItemModelNews(String imageUrl, String date, String title, String description) {
        this.imageUrl = imageUrl;
        this.date = date;
        this.title = title;
        this.description = description;
    }

    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_NEWS;
    }
}
