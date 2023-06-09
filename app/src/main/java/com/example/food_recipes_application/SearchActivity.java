package com.example.food_recipes_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    SearchView searchView;
    String searchText;
    Boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Search...");

        SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            BottomNavigationFragment bottomNavigationFragment = new BottomNavigationFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerSearchNav, bottomNavigationFragment);
            transaction.commit();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerSearchNav);
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
            }
        }

        searchView = findViewById(R.id.searchView);

        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchText = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                return false;
            }
        });
    }

    public void submitAndGoToRecipeSearchResult(View view){
        if(searchText.equals("")) {
            Toast.makeText(this, "Please write in the search bar", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(SearchActivity.this, RecipeSearchResultActivity.class);
            intent.putExtra("keyword", searchText);
            startActivity(intent);
        }
    }

    public void goBackToInitialOrWelcomeActivity(View view) {
        Intent intent;
        if(isLoggedIn) {
            intent = new Intent(this, WelcomeActivity.class);
        }
        else {
            intent = new Intent(this, InitialActivity.class);
        }
        startActivity(intent);
    }
}