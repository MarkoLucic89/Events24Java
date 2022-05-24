package com.lucic.cubes.events24.data.model.events_detail_model;

public class ItemModelTitle extends EventsDetailItemModel {

    public String title;

    public ItemModelTitle() {
    }

    public ItemModelTitle(String title) {
        this.title = title;
    }


    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_TITLE;
    }
}
