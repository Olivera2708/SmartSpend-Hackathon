package com.example.smartspend.navigation;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspend.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class NavBar {
    public static void setNavigationBar(BottomNavigationView navigationView, AppCompatActivity context, int selectedItem) {
        navigationView.getMenu().clear();
        navigationView.setOnItemSelectedListener(null);

        navigationView.inflateMenu(R.menu.navbar);
        navigationView.setSelectedItemId(selectedItem);
        navigationView.setOnItemSelectedListener(new Navigation(context));


    }
}
