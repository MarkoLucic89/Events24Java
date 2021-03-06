package com.lucic.cubes.events24.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.databinding.RvItemEventsBigBinding;
import com.lucic.cubes.events24.databinding.RvItemEventsSearchBinding;
import com.lucic.cubes.events24.databinding.RvItemEventsSmallBinding;
import com.lucic.cubes.events24.ui.activity.EventsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private Context context;
    private ArrayList<Event> events;
    private int resourceId;
    private OnEventsClickListener listener;

    public EventsAdapter(Context context, ArrayList<Event> events, int resourceId) {
        this.context = context;
        this.events = events;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (resourceId == R.layout.rv_item_events_big) {
            RvItemEventsBigBinding binding = RvItemEventsBigBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new EventsViewHolder(binding);
        } else if (resourceId == R.layout.rv_item_events_search) {
            RvItemEventsSearchBinding binding = RvItemEventsSearchBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new EventsViewHolder(binding);
        } else {
            RvItemEventsSmallBinding binding = RvItemEventsSmallBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new EventsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        Event event = events.get(position);

        if (holder.bindingBig != null) {
            holder.bindingBig.textViewTitle.setText(event.title);
            holder.bindingBig.textViewDate.setText(event.date);
            Picasso.get().load(event.imageBig).into(holder.bindingBig.imageView);

//            holder.bindingBig.getRoot().setOnClickListener(view -> listener.onEventClick(event));
            holder.bindingBig.getRoot().setOnClickListener(view -> {

                Intent intent = new Intent(context.getApplicationContext(), EventsDetailActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
            });


        } else if (holder.bindingSearch != null) {
            holder.bindingSearch.textViewTitle.setText(event.title);
            holder.bindingSearch.textViewDate.setText(event.date);
            Picasso.get().load(event.imageSmall).into(holder.bindingSearch.imageView);

//            holder.bindingSearch.getRoot().setOnClickListener(view -> listener.onEventClick(event));

            holder.bindingSearch.getRoot().setOnClickListener(view -> {

                Intent intent = new Intent(context.getApplicationContext(), EventsDetailActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
            });

        } else {
            holder.bindingSmall.textViewTitle.setText(event.title);
            holder.bindingSmall.textViewDate.setText(event.date);
            Picasso.get().load(event.imageSmall).into(holder.bindingSmall.imageView);

//            holder.bindingSmall.getRoot().setOnClickListener(view -> listener.onEventClick(event));

            holder.bindingSmall.imageView.setOnClickListener(view -> {

                Intent intent = new Intent(context.getApplicationContext(), EventsDetailActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
            });

        }

    }

    @Override
    public int getItemCount() {
        if (events == null) {
            return 0;
        }
        return events.size();
    }

    public void setListener(OnEventsClickListener listener) {
        this.listener = listener;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        private RvItemEventsBigBinding bindingBig;
        private RvItemEventsSmallBinding bindingSmall;
        private RvItemEventsSearchBinding bindingSearch;

        public EventsViewHolder(RvItemEventsBigBinding binding) {
            super(binding.getRoot());
            this.bindingBig = binding;
        }

        public EventsViewHolder(RvItemEventsSmallBinding binding) {
            super(binding.getRoot());
            this.bindingSmall = binding;
        }

        public EventsViewHolder(RvItemEventsSearchBinding binding) {
            super(binding.getRoot());
            this.bindingSearch = binding;
        }
    }

    private interface OnEventsClickListener {
        void onEventClick(Event event);
    }

}
