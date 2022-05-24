package com.lucic.cubes.events24.data.model.events_detail_model;

public class ItemModelTicket extends EventsDetailItemModel {

    public String title;
    public String price;

    public ItemModelTicket() {
    }

    public ItemModelTicket(String title, String price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public int getType() {
        return EventsDetailItemModel.TYPE_TICKET;
    }
}
