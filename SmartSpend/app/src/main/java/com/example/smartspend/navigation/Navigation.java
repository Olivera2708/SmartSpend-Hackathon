package com.example.smartspend.navigation;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartspend.MainActivity;
import com.example.smartspend.ProfileActivity;
import com.example.smartspend.R;
import com.google.android.material.navigation.NavigationBarView;

public class Navigation implements NavigationBarView.OnItemSelectedListener{
    private final AppCompatActivity currentActivity;
    public Navigation(AppCompatActivity currentActivity){
        this.currentActivity = currentActivity;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_home) {
            Intent intent = new Intent(currentActivity, MainActivity.class);
            currentActivity.startActivity(intent);
            currentActivity.overridePendingTransition(0,0);
            currentActivity.finish();
//        } else if (item.getItemId() == R.id.navigation_chat) {
//            Intent intent = new Intent(currentActivity, AccountDetailsActivity.class);
//            currentActivity.startActivity(intent);
//            currentActivity.overridePendingTransition(0,0);
//            currentActivity.finish();
//        } else if (item.getItemId() == R.id.navigation_charts) {
//            Intent intent = new Intent(currentActivity, AllUsersActivity.class);
//            currentActivity.startActivity(intent);
//            currentActivity.overridePendingTransition(0,0);
//            currentActivity.finish();
        } else if (item.getItemId() == R.id.navigation_profile) {
            Intent intent = new Intent(currentActivity, ProfileActivity.class);
            currentActivity.startActivity(intent);
            currentActivity.overridePendingTransition(0,0);
            currentActivity.finish();
        }
        return false;
    }
}
