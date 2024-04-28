package com.example.smartspend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smartspend.navigation.NavBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientUtils.setClientUtils(getSharedPreferences("sharedPref", MODE_PRIVATE));
        NavBar.setNavigationBar(findViewById(R.id.bottom_navigaiton), this, R.id.navigation_home);

    }
}