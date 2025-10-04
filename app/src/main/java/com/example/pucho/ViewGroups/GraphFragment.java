package com.example.pucho.ViewGroups;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.R;
import com.example.pucho.controladores.AlarmAndBDController;
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
import java.util.Collections;
import java.util.List;

public class GraphFragment extends Fragment {
    private static LineChart lineChart;
    private static ArrayList<PuchoDia> arrayList;
    private static List<String> list;
    private static List<Float> listConsumo, listExp;
    private static int count;
    private static View contentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_graph, container, false);

        setLineChart(contentView.findViewById(R.id.lineal_chart));

        return contentView;
    }
    public static void upload(){
        setLineChart(lineChart);
    }
    public static void setLineChart(LineChart l){
        lineChart = l;
        arrayList = AlarmAndBDController.get30Dias();
        list = new ArrayList<>();
        listConsumo = new ArrayList<>();
        listExp = new ArrayList<>();

        Description description = new Description();
        description.setText("CONSUMO");
        description.setPosition(150f,15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        Collections.reverse(arrayList);

        arrayList.forEach(puchoDia -> {
            list.add(puchoDia.getFecha());
            listConsumo.add((float)puchoDia.getConsumo());
            listExp.add((float)puchoDia.getExpectativa());
        });

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));
        xAxis.setLabelRotationAngle(90f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);

        xAxis.setLabelCount(list.size());
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(25f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.WHITE);
        yAxis.setLabelCount(10);
        yAxis.setTextColor(Color.WHITE);

        List<Entry> entriesConsumo = new ArrayList<>();
        count = 0;
        listConsumo.forEach(consumo -> {
            entriesConsumo.add(new Entry(count, consumo ));
            count++;
        });
        List<Entry> entriesExp = new ArrayList<>();
        count = 0;
        listExp.forEach(exp -> {
            entriesExp.add(new Entry(count, exp ));
            count++;
        });

        LineDataSet dataSetConsumo = new LineDataSet(entriesConsumo, "CONSUMO");
        dataSetConsumo.setColor(Color.BLUE);
        dataSetConsumo.setDrawCircles(false);
        dataSetConsumo.setDrawValues(true);
        dataSetConsumo.setValueTextColor(Color.WHITE);

        LineDataSet dataSetExp = new LineDataSet(entriesExp, "META");
        dataSetExp.setColor(Color.RED);
        dataSetExp.setDrawCircles(false);
        dataSetExp.setDrawValues(false);

        LineData lineData = new LineData(dataSetConsumo, dataSetExp);
        lineChart.getLegend().setTextColor(Color.WHITE);
        lineChart.setData(lineData);

    }
}