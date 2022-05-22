package com.lucic.cubes.events24.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.databinding.FragmentHomeBinding;
import com.lucic.cubes.events24.databinding.FragmentSearchBinding;
import com.lucic.cubes.events24.ui.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentSearchBinding binding;

    private ArrayList<Event> events;

    private SearchAdapter searchAdapter;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        events = new ArrayList<>();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initSearchAdapter();
        setSearchFilterListener();

    }

    private void setSearchFilterListener() {
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                ArrayList<Event> searchList = new ArrayList<>();

                if (editable.toString().isEmpty()) {
                    updateResults(searchList);
                    return;
                }

                db.collection("events")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                    String title = (String) documentSnapshot.get("title");

                                    if (title.toLowerCase().contains(binding.editTextSearch.getText().toString().toLowerCase())) {
                                        Event event = new Event(documentSnapshot.getData());
                                        searchList.add(event);
                                    }
                                }

                                updateResults(searchList);
                            }
                        });
            }
        });

    }

    private void updateResults(ArrayList<Event> searchList) {

        if (searchList.isEmpty()) {
            binding.textViewSearchResults.setText("0 results");
        } else {
            binding.textViewSearchResults.setText("" + searchList.size() + " rezultata");
        }

        searchAdapter.updateList(searchList);

    }

    private void initSearchAdapter() {
        searchAdapter = new SearchAdapter(this.getContext(), events);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        binding.recyclerView.setAdapter(searchAdapter);
    }


}