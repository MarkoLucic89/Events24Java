package com.lucic.cubes.events24.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.DataContainer;
import com.lucic.cubes.events24.data.SharedPrefs;
import com.lucic.cubes.events24.data.model.User;
import com.lucic.cubes.events24.databinding.FragmentProfileBinding;
import com.lucic.cubes.events24.tools.LanguageTool;
import com.lucic.cubes.events24.ui.activity.EditProfileActivity;
import com.lucic.cubes.events24.ui.activity.SplashScreenActivity;
import com.lucic.cubes.events24.ui.activity.registration.HomeActivity;
import com.lucic.cubes.events24.ui.activity.registration.MainRegistrationActivity;
import com.lucic.cubes.events24.ui.view.EventsMessage;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLanguage.setText(getCurrentLanguage());

        binding.switchNotifications.setChecked(SharedPrefs.getInstance(getActivity()).isNotificationOn());

        setListeners();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (DataContainer.user != null) {

            updateUserUi(DataContainer.user);

        } else {
            String userId = auth.getCurrentUser().getUid();

            db.collection("users")
                    .whereEqualTo("id", userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            if (queryDocumentSnapshots.getDocuments().size() == 1) {
                                User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                                DataContainer.user = user;
                                updateUserUi(user);
                            }

                        }
                    });
        }
    }

    private String getCurrentLanguage() {
        String[] array = getResources().getStringArray(R.array.app_languages);
        int index = SharedPrefs.getInstance(getActivity()).getAppLanguage();
        return array[index];
    }

    private void setListeners() {
        binding.buttonLogout.setOnClickListener(view -> logOut());
        binding.textViewHelpCentre.setOnClickListener(view -> helpCentre());
        binding.textViewTermsAndConditions.setOnClickListener(view -> termsAndConditions());
        binding.switchNotifications.setOnCheckedChangeListener((compoundButton, b) -> handleSwitch(b));
        binding.buttonLanguage.setOnClickListener(view -> setLanguage());

        binding.imageViewProfileImage.setOnClickListener(view -> editProfile());
        binding.textViewEmail.setOnClickListener(view -> editProfile());
        binding.textViewName.setOnClickListener(view -> editProfile());
    }

    private void editProfile() {
        Intent i = new Intent(this.getActivity(), EditProfileActivity.class);
        startActivity(i);
    }

    private void handleSwitch(boolean b) {
        SharedPrefs.getInstance(getActivity()).setNotificationStatus(b);

        if (b) {
            FirebaseMessaging.getInstance().subscribeToTopic("general");
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("general");
        }
    }

    private void setLanguage() {
        int selectedIndex = SharedPrefs.getInstance(getActivity()).getAppLanguage();
        String[] array = getResources().getStringArray(R.array.app_languages);

        if (selectedIndex == array.length - 1) {
            //dosao sam do kraja niza, vrati index na 0
            SharedPrefs.getInstance(getActivity()).saveAppLanguage(0);
            binding.buttonLanguage.setText(array[0]);

        } else {
            selectedIndex++;
            SharedPrefs.getInstance(getActivity()).saveAppLanguage(selectedIndex);
            binding.buttonLanguage.setText(array[selectedIndex]);

        }

        LanguageTool.setLanguageResourceConfiguration(getActivity());

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("start_profile", true);
        getActivity().finish();
        startActivity(intent);

    }

    private void helpCentre() {
        EventsMessage.showMessage(getContext(), "In progress...");
    }

    private void termsAndConditions() {
        EventsMessage.showMessage(getContext(), "In progress...");
    }

    private void logOut() {
        auth.signOut();
        Intent intent = new Intent(getContext(), SplashScreenActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void updateUserUi(User user) {
        if (user.imageUrl != null && user.imageUrl.length() > 0) {
            Picasso.get().load(user.imageUrl).into(binding.imageViewProfileImage);
        }

        binding.textViewEmail.setText(user.email);
        binding.textViewName.setText(user.name);
    }
}