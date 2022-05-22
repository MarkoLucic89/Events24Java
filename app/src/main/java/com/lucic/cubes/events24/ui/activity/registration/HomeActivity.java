package com.lucic.cubes.events24.ui.activity.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.databinding.ActivityHomeBinding;
import com.lucic.cubes.events24.ui.activity.BasicActivity;
import com.lucic.cubes.events24.ui.fragment.FavoritesFragment;
import com.lucic.cubes.events24.ui.fragment.HomeFragment;
import com.lucic.cubes.events24.ui.fragment.ProfileFragment;
import com.lucic.cubes.events24.ui.fragment.SearchFragment;
import com.lucic.cubes.events24.ui.fragment.TicketsFragment;
import com.lucic.cubes.events24.ui.view.EventsMessage;

public class HomeActivity extends BasicActivity {

    private ActivityHomeBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        boolean isStartedFromProfile = getIntent().getBooleanExtra("start_profile", false);


        initFirebase();
        initBottomNavigation();

        if (isStartedFromProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ProfileFragment.newInstance()).commit();
            binding.bottomNavigationView.setSelectedItemId(R.id.profile);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.newInstance()).commit();
        }
    }

    private void initBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance();

                        break;
                    case R.id.search:
                        selectedFragment = SearchFragment.newInstance();

                        break;
                    case R.id.tickets:
                        selectedFragment = TicketsFragment.newInstance();

                        break;
                    case R.id.favorites:
                        selectedFragment = FavoritesFragment.newInstance();

                        break;
                    case R.id.profile:
                        selectedFragment = ProfileFragment.newInstance();

                        break;
                    default:
                        selectedFragment = HomeFragment.newInstance();


                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            }
        });
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
    }


    private void logout() {
        auth.signOut();
        goToLoginActivity();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}