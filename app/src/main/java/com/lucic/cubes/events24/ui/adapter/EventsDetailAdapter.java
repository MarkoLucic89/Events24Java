package com.lucic.cubes.events24.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.data.model.News;
import com.lucic.cubes.events24.data.model.Ticket;
import com.lucic.cubes.events24.data.model.events_detail_model.EventsDetailItemModel;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelAboutArtist;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelHeader;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelNews;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelPictures;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelTicket;
import com.lucic.cubes.events24.data.model.events_detail_model.ItemModelTitle;
import com.lucic.cubes.events24.databinding.RvItemDetailsEventAboutArtistBinding;
import com.lucic.cubes.events24.databinding.RvItemDetailsEventsHeaderBinding;
import com.lucic.cubes.events24.databinding.RvItemDetailsEventsNewsBinding;
import com.lucic.cubes.events24.databinding.RvItemDetailsEventsPicturesBinding;
import com.lucic.cubes.events24.databinding.RvItemDetailsEventsTitleBinding;
import com.lucic.cubes.events24.databinding.RvItemDetailsTicketsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Event event;
    private ArrayList<EventsDetailItemModel> list;
    private ArrayList<Ticket> tickets;
    private Context context;

    public EventsDetailAdapter(Context context, Event event) {

        this.context = context;
        this.event = event;

        list = new ArrayList<>();
        tickets = new ArrayList<>();

        // HEADER

        ItemModelHeader modelHeader = new ItemModelHeader(
                event.imageBig,
                event.type,
                event.title,
                event.date,
                event.location
        );

        list.add(modelHeader);


        if (!event.tickets.isEmpty()) {

            //TITLE TICKETS

            ItemModelTitle modelTitle = new ItemModelTitle(context.getString(R.string.menu_tickets));

            list.add(modelTitle);

            //TICKETS

            tickets = event.tickets;

            for (Ticket ticket : tickets) {
                ItemModelTicket modelTicket = new ItemModelTicket(ticket.title, ticket.price);
                list.add(modelTicket);
            }
        }


        if (!event.pictures.isEmpty()) {

            //TITLE PICTURES AND VIDEOS

            ItemModelTitle modelTitlePictures = new ItemModelTitle(context.getString(R.string.pictures_and_video));

            list.add(modelTitlePictures);


            //PICTURES AND VIDEO

            ItemModelPictures modelPictures = new ItemModelPictures(event.pictures);

            list.add(modelPictures);
        }


        if (!event.aboutArtist.imageUrl.isEmpty() && !event.aboutArtist.description.isEmpty()) {

            //TITLE ABOUT ARTIST

            ItemModelTitle modelTitleAboutArtist = new ItemModelTitle(context.getString(R.string.about_artist));

            list.add(modelTitleAboutArtist);


            //ABOUT ARTIST

            ItemModelAboutArtist modelAboutArtist = new ItemModelAboutArtist(
                    event.aboutArtist.imageUrl,
                    event.aboutArtist.description
            );

            list.add(modelAboutArtist);

        }

        if (!event.newsList.isEmpty()) {

            //TITLE NEWS

            ItemModelTitle modelTitleNews = new ItemModelTitle(context.getString(R.string.News));

            list.add(modelTitleNews);

            //NEWS

            for (News news : event.newsList) {

                ItemModelNews modelNews = new ItemModelNews(
                        news.imageUrl,
                        news.date,
                        news.title,
                        news.description
                );

                list.add(modelNews);

            }

        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        switch (viewType) {
            case EventsDetailItemModel.TYPE_HEADER:
                RvItemDetailsEventsHeaderBinding headerBinding = RvItemDetailsEventsHeaderBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new HeaderViewHolder(headerBinding);

            case EventsDetailItemModel.TYPE_TITLE:
                RvItemDetailsEventsTitleBinding titleBinding = RvItemDetailsEventsTitleBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new TitleViewHolder(titleBinding);

            case EventsDetailItemModel.TYPE_TICKET:
                RvItemDetailsTicketsBinding ticketsBinding = RvItemDetailsTicketsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new TicketViewHolder(ticketsBinding);

            case EventsDetailItemModel.TYPE_PICTURES:
                RvItemDetailsEventsPicturesBinding picturesBinding = RvItemDetailsEventsPicturesBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new PicturesViewHolder(picturesBinding);

            case EventsDetailItemModel.TYPE_ABOUT_ARTIST:
                RvItemDetailsEventAboutArtistBinding aboutArtistBinding = RvItemDetailsEventAboutArtistBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new AboutArtistViewHolder(aboutArtistBinding);

            case EventsDetailItemModel.TYPE_NEWS:
                RvItemDetailsEventsNewsBinding newsBinding = RvItemDetailsEventsNewsBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                );
                return new NewsViewHolder(newsBinding);

            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        EventsDetailItemModel model = list.get(position);

        if (model.getType() == EventsDetailItemModel.TYPE_HEADER) {

            ItemModelHeader itemModelHeader = (ItemModelHeader) model;

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            Picasso.get().load(itemModelHeader.imageUrl).into(headerViewHolder.binding.imageView);
            headerViewHolder.binding.textViewType.setText(itemModelHeader.type);
            headerViewHolder.binding.textViewTitle.setText(itemModelHeader.title);
            headerViewHolder.binding.textViewDate.setText(itemModelHeader.date);
            headerViewHolder.binding.textViewPlace.setText(itemModelHeader.location);

        } else if (model.getType() == EventsDetailItemModel.TYPE_TITLE) {

            ItemModelTitle itemModelTitle = (ItemModelTitle) model;

            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;

            titleViewHolder.binding.textViewTitle.setText(itemModelTitle.title);

        } else if (model.getType() == EventsDetailItemModel.TYPE_TICKET) {

            ItemModelTicket itemModelTicket = (ItemModelTicket) model;

            TicketViewHolder ticketViewHolder = (TicketViewHolder) holder;

            ticketViewHolder.binding.textViewTitle.setText(itemModelTicket.title);
            ticketViewHolder.binding.textViewPrice.setText(itemModelTicket.price);

        } else if (model.getType() == EventsDetailItemModel.TYPE_PICTURES) {

            ItemModelPictures modelPictures = (ItemModelPictures) model;

            PicturesViewHolder picturesViewHolder = (PicturesViewHolder) holder;

            ArtistImagesAdapter adapter = new ArtistImagesAdapter(modelPictures.list);
            picturesViewHolder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(
                    context,
                    RecyclerView.HORIZONTAL,
                    false
            ));
            picturesViewHolder.binding.recyclerView.setAdapter(adapter);


        } else if (model.getType() == EventsDetailItemModel.TYPE_ABOUT_ARTIST) {

            ItemModelAboutArtist itemModelAboutArtist = (ItemModelAboutArtist) model;

            AboutArtistViewHolder aboutArtistViewHolder = (AboutArtistViewHolder) holder;

            Picasso.get().load(itemModelAboutArtist.imageUrl).into(aboutArtistViewHolder.binding.imageView);
            aboutArtistViewHolder.binding.textViewDetails.setText(itemModelAboutArtist.description);

        } else if (model.getType() == EventsDetailItemModel.TYPE_NEWS) {

            ItemModelNews modelNews = (ItemModelNews) model;

            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;

            Picasso.get().load(modelNews.imageUrl).into(newsViewHolder.binding.imageView);
            newsViewHolder.binding.textViewDate.setText(modelNews.date);
            newsViewHolder.binding.textViewTitle.setText(modelNews.title);
            newsViewHolder.binding.textViewDetails.setText(modelNews.description);
        }


    }

    @Override
    public int getItemViewType(int position) {

        EventsDetailItemModel model = list.get(position);

        return model.getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsEventsHeaderBinding binding;

        public HeaderViewHolder(RvItemDetailsEventsHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsEventsTitleBinding binding;

        public TitleViewHolder(RvItemDetailsEventsTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsTicketsBinding binding;

        public TicketViewHolder(RvItemDetailsTicketsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class PicturesViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsEventsPicturesBinding binding;

        public PicturesViewHolder(RvItemDetailsEventsPicturesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    class AboutArtistViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsEventAboutArtistBinding binding;

        public AboutArtistViewHolder(RvItemDetailsEventAboutArtistBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private RvItemDetailsEventsNewsBinding binding;

        public NewsViewHolder(RvItemDetailsEventsNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
