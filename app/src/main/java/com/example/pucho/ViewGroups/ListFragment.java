package com.example.pucho.ViewGroups;


import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pucho.ADAPTADORES.PuchoListAdapter;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private static MainViewModel viewModel;
    private ListView listView;
    private View contentView, headerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_list, container, false);
        //setListView(contentView.getId());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        listView = contentView.findViewById(R.id.listView);
        headerView = contentView.inflate(listView.getContext(),R.layout.header_list_view,null);
        listView.addHeaderView(headerView,null,false);

        viewModel.getModel().observe(getViewLifecycleOwner(), new Observer<ArrayList<PuchoDia>>() {
            @Override
            public void onChanged(ArrayList<PuchoDia> puchoDias) {
                PuchoListAdapter adapter = new PuchoListAdapter(getContext(), puchoDias);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                PuchoDia dia = (PuchoDia) parent.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eliminar el día");
                builder.setMessage(dia.getFecha());
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Dismiss the dialog when OK is clicked
                    }
                });
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dia != null) {
                            viewModel.delete_pucho(dia.get_id());

                        }else{
                            Toast.makeText(getContext(), "No se encuentra el elemento", Toast.LENGTH_LONG);
                        }
                        dialog.dismiss(); // Dismiss the dialog when OK is clicked
                    }
                });
                builder.show();
            }
        });
        return contentView;
    }
}