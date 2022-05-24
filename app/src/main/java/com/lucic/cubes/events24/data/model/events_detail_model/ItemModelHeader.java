package com.lucic.cubes.events24.data.model.events_detail_model;

public class ItemModelHeader extends EventsDetailItemModel {

    public String imageUrl;
    public String type;
    public String title;
    public String date;
    public String location;

    public ItemModelHeader() {
    }

    public ItemModelHeader(String imageUrl, String type, String title, String date, String location) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.title = title;
        this.date = date;
        this.location = location;
    }

    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_HEADER;
    }
}
