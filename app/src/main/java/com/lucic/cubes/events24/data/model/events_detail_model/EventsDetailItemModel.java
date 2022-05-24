package com.lucic.cubes.events24.data.model.events_detail_model;

public abstract class EventsDetailItemModel {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_TITLE = 2;
    public static final int TYPE_TICKET = 3;
    public static final int TYPE_PICTURES = 4;
    public static final int TYPE_ABOUT_ARTIST = 5;
    public static final int TYPE_NEWS = 6;

    public abstract int getType();
}
