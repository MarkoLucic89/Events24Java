package com.lucic.cubes.events24.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.databinding.RvItemEventsSearchBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<Event> events;

    public SearchAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemEventsSearchBinding binding = RvItemEventsSearchBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
        );
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Event event = events.get(position);
        holder.binding.textViewTitle.setText(event.title);
        holder.binding.textViewDate.setText(event.date);
        Picasso.get().load(event.imageBig).into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateList(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        RvItemEventsSearchBinding binding;

        public SearchViewHolder(RvItemEventsSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
