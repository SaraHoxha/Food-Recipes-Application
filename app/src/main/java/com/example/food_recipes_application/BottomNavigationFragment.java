package com.example.food_recipes_application;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
                return true;
            } else if (itemId == R.id.menu_search) {
                startActivity(new Intent(getActivity(), InitialActivity.class));
                return true;
            } else if (itemId == R.id.menu_love) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
                return true;
            }

            return false;
        });

        return view;
    }
}