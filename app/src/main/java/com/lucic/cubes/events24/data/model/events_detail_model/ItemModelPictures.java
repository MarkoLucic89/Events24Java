package com.lucic.cubes.events24.data.model.events_detail_model;

import java.util.ArrayList;

public class ItemModelPictures extends EventsDetailItemModel {

    public ArrayList<String> list;

    public ItemModelPictures() {
        list = new ArrayList<>();
    }

    public ItemModelPictures(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_PICTURES;
    }
}
