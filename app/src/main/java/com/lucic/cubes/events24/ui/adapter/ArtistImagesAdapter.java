package com.lucic.cubes.events24.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucic.cubes.events24.databinding.RvItemPictureBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistImagesAdapter extends RecyclerView.Adapter<ArtistImagesAdapter.ArtistImagesViewHolder> {

    private ArrayList<String> list;

    public ArtistImagesAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ArtistImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvItemPictureBinding binding = RvItemPictureBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ArtistImagesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistImagesViewHolder holder, int position) {

        String imageUrl = list.get(position);

        Picasso.get().load(imageUrl).into(holder.binding.imageViewPicture);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ArtistImagesViewHolder extends RecyclerView.ViewHolder {

        private RvItemPictureBinding binding;

        public ArtistImagesViewHolder(RvItemPictureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
