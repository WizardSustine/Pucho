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
import java.util.List;

public class GraphFragment extends Fragment {
    private LineChart lineChart;
    private ArrayList<PuchoDia> arrayList;
    private List<String> list;
    private List<Float> listConsumo, listExp;
    private int count;
    private AlarmAndBDController alarmAndBDController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_graph, container, false);

        alarmAndBDController = new AlarmAndBDController(contentView.getContext());
        arrayList = alarmAndBDController.get30Dias();
        
        lineChart = contentView.findViewById(R.id.lineal_chart);
        list = new ArrayList<>();
        listConsumo = new ArrayList<>();
        listExp = new ArrayList<>();

        Description description = new Description();
        description.setText("CONSUMO");
        description.setPosition(150f,15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        arrayList.forEach(puchoDia -> {
            list.add(puchoDia.getFecha());
            listConsumo.add((float)puchoDia.getConsumo());
            listExp.add((float)puchoDia.getExpectativa());
        });

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        xAxis.setLabelCount(list.size());
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(40f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

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

        LineDataSet dataSetExp = new LineDataSet(entriesExp, "META");
        dataSetExp.setColor(Color.RED);

        LineData lineDataConsumo = new LineData(dataSetConsumo);


        LineData lineDataMeta = new LineData(dataSetConsumo);

        lineChart.setData(lineDataConsumo);

        lineChart.setData(lineDataMeta);

        return contentView;
    }

}