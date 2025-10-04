package com.example.pucho.ViewGroups;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.pucho.R;
import com.example.pucho.controladores.AlarmAndBDController;

public class ListFragment extends Fragment {
    private static ListView listView;
    private static View contentView, headerView;
    private static SimpleCursorAdapter adapter;
    private static AlarmAndBDController alarmAndBDController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_list, container, false);
        setListView(contentView.findViewById(R.id.listView));
        headerView = contentView.inflate(listView.getContext(),R.layout.header_list_view,null);
        listView.addHeaderView(headerView);

        alarmAndBDController = new AlarmAndBDController(contentView.getContext());
        return contentView;
    }
    public static void upload(){
        setListView(listView);
    }
    private static void setListView(ListView l){
        listView = l;
        adapter = alarmAndBDController.getAdapter();
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

            }
        });
    }
}