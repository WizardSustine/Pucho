package com.example.pucho.ViewGroups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pucho.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphFragment extends Fragment {
    private LineChart lineChart;
    private List<String> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_graph, container, false);

        lineChart = contentView.findViewById(R.id.lineal_chart);

        Description description = new Description();
        description.setText("CONSUMO");
        description.setPosition(150f,15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        list = Arrays.asList("2025-09-25", "2025-09-26", "2025-09-27", "2025-09-28");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(-15f);
        yAxis.setAxisMaximum(+15f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, 5f));
        entries.add(new Entry(1, -10f));
        entries.add(new Entry(2, 0f));
        entries.add(new Entry(3, -3f));

        LineDataSet dataSet = new LineDataSet(entries, "FECHA");
        dataSet.setColor(Color.BLUE);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        return contentView;
    }

}