package com.example.pucho.ViewGroups;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.pucho.R;
import com.example.pucho.controladores.AlarmAndBDController;

public class ListFragment extends Fragment {
    private static ListView listView;
    private static View contentView;
    private static SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_list, container, false);
        setListView(contentView.findViewById(R.id.listView));
        View headerView = contentView.inflate(listView.getContext(),R.layout.header_list_view,null);
        listView.addHeaderView(headerView);
        //instance = this;

        return contentView;
    }
    public static void upload(){
        setListView(listView);
    }
    private static void setListView(ListView l){
        listView = l;
        adapter = AlarmAndBDController.getAdapter();
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

            }
        });
    }
}