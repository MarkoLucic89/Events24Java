package com.lucic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.Event;
import com.lucic.cubes.events24.databinding.ActivityEventsDetailBinding;
import com.lucic.cubes.events24.ui.adapter.EventsDetailAdapter;

public class EventsDetailActivity extends AppCompatActivity {

    private final String TAG = "EventsDetailActivity";

    private ActivityEventsDetailBinding binding;
    private EventsDetailAdapter adapter;
    private Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        event = (Event) getIntent().getSerializableExtra("event");

        initRecyclerView();
        setListeners();

    }


    private void setListeners() {
        binding.imageViewBack.setOnClickListener(view -> finish());
        binding.imageViewShare.setOnClickListener(view -> share());
    }

    private void share() {
        createDynamicLink();
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventsDetailAdapter(this, event);
        binding.recyclerView.setAdapter(adapter);
    }

    private void createDynamicLink() {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.events24java.com/"))
                .setDomainUriPrefix("https://lucic.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();



        Uri dynamicLinkUri = dynamicLink.getUri();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, dynamicLinkUri.toString());
        intent.putExtra(Intent.EXTRA_TEXT, dynamicLinkUri.toString());
        startActivity(Intent.createChooser(intent, "Share via"));

        Toast.makeText(this, "DYNAMIC LINK: " + dynamicLinkUri, Toast.LENGTH_SHORT).show();

    }


    private void getDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                        }

                        if (deepLink != null) {

                            Toast.makeText(EventsDetailActivity.this, deepLink.toString(), Toast.LENGTH_SHORT).show();
                        }

                        // Handle the deep link. For example, open the linked content,
                        // or apply promotional credit to the user's account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,  e.getMessage().toString());
                        Toast.makeText(EventsDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }




}