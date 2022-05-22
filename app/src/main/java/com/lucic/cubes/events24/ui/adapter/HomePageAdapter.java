package com.lucic.cubes.events24.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.databinding.RvItemHorizontalRvBinding;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Event> events;
    private ArrayList<Event> concerts;
    private ArrayList<Event> sports;
    private ArrayList<Event> theatres;
    private ArrayList<Event> topList;

    public HomePageAdapter(Context context, ArrayList<Event> topEventsList, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
        this.sports = new ArrayList<>();
        this.concerts = new ArrayList<>();
        this.theatres = new ArrayList<>();

        if (topList != null) {
            topList = topEventsList;
        } else {
            topList = new ArrayList<>();
        }

        this.topList = topEventsList;

        if (events != null) {
            for (Event event : events) {
                if (event.type.equalsIgnoreCase("Koncert")) {
                    concerts.add(event);
                } else if (event.type.equalsIgnoreCase("Sport")) {
                    sports.add(event);
                } else if (event.type.equalsIgnoreCase("Pozoriste")) {
                    theatres.add(event);
                }
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemHorizontalRvBinding binding = RvItemHorizontalRvBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FirstViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {

            FirstViewHolder firstHolder = (FirstViewHolder) holder;

            firstHolder.binding.textViewType.setText(R.string.top_events);
            firstHolder.binding.recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, topList, R.layout.rv_item_events_big));

        } else if (position == 1) {
            FirstViewHolder firstHolder = (FirstViewHolder) holder;

            firstHolder.binding.textViewType.setText(R.string.concerts);
            firstHolder.binding.recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, concerts, R.layout.rv_item_events_big));

        } else if (position == 2) {
            FirstViewHolder firstHolder = (FirstViewHolder) holder;

            firstHolder.binding.textViewType.setText(R.string.theatre);
            firstHolder.binding.recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, theatres, R.layout.rv_item_events_small));

        } else if (position == 3) {
            FirstViewHolder firstHolder = (FirstViewHolder) holder;

            firstHolder.binding.textViewType.setText(R.string.sport);
            firstHolder.binding.recyclerView.setLayoutManager(
                    new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            firstHolder.binding.recyclerView.setAdapter(new EventsAdapter(context, sports, R.layout.rv_item_events_big));

        }

    }

    private ArrayList<Event> getEventsByType(ArrayList<Event> events, String type) {
        for (Event event : events) {
            if (event.type == type) {
                events.add(event);
            }
        }
        return events;
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    private class FirstViewHolder extends RecyclerView.ViewHolder {

        private RvItemHorizontalRvBinding binding;

        public FirstViewHolder(RvItemHorizontalRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
