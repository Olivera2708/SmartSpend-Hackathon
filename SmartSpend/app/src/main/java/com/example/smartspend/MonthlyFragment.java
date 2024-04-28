package com.example.smartspend;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthlyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthlyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Activity activity;
    LineChart lineChart;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    public MonthlyFragment(Activity activity) {
        this.activity = activity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonthlyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthlyFragment newInstance(String param1, String param2) {
        MonthlyFragment fragment = new MonthlyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MonthlyFragment newInstance(Activity activity){
        MonthlyFragment fragment = new MonthlyFragment(activity);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        lineChart = view.findViewById(R.id.chart1);

        getData();
        return view;
    }

    private void getData(){
        List<Entry> lineEntries1 = new ArrayList<>();
        int balance = 1230;
        lineEntries1.add(new Entry(0, balance));
        Random random = new Random();

        for (int i = 1; i < 10; i++) {
            lineEntries1.add(new Entry(i, balance - 1 - random.nextInt(100)));
        }

//        int textColor = isDarkTheme(getContext()) ? Color.WHITE : Color.BLACK;

        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1, "Monthly transactions");
        lineDataSet1.setColor(Color.rgb(168,217,246));
        lineDataSet1.setCircleColor(Color.rgb(168,217,246));
        lineDataSet1.setLineWidth(3f);
        lineDataSet1.setCircleRadius(3f);
        lineDataSet1.setValueTextSize(12f);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateX(1500, Easing.EaseInOutQuart);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawAxisLine(true);
        lineChart.getAxisRight().setEnabled(false);

        Legend legend = lineChart.getLegend();

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(false);
        lineChart.setPinchZoom(false);

        lineChart.invalidate();
    }
}