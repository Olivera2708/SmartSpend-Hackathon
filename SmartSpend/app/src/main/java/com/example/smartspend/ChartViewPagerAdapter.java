package com.example.smartspend;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ChartViewPagerAdapter extends FragmentStateAdapter {

    public ChartViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Activity activity) {
        super(fragmentActivity);
        this.activity = activity;
    }

    private Activity activity;
    public ChartViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.activity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return CategoryFragment.newInstance(activity);
            default:
                return MonthlyFragment.newInstance(activity);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
