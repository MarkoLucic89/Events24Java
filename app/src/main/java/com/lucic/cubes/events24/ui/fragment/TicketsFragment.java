package com.lucic.cubes.events24.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucic.cubes.events24.R;

public class TicketsFragment extends Fragment {

    public static TicketsFragment newInstance() {
        TicketsFragment fragment = new TicketsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }
}