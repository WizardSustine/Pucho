package com.example.pucho.ADAPTADORES;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.R;

import java.util.ArrayList;

public class PuchoListAdapter extends ArrayAdapter<PuchoDia> {
    // Un adaptador de vista para mi ListView.
    // Adapta un array de objetos PuchoDia
    // Asigna los valores del objeto PuchoDia a los distintos textviews
    public PuchoListAdapter(@NonNull Context context, ArrayList<PuchoDia> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.view_consumo, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        PuchoDia currentPuchoPosition = getItem(position);


        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.id_ListViewPuchos);
        textView1.setText(String.valueOf(currentPuchoPosition.get_id()));

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView textView2 = currentItemView.findViewById(R.id.dateListViewPuchos);
        textView2.setText(currentPuchoPosition.getFecha());

        // then according to the position of the view assign the desired TextView 3 for the same
        TextView textView3 = currentItemView.findViewById(R.id.cantidadListViewPuchos);
        textView3.setText(String.valueOf(currentPuchoPosition.getConsumo()));

        // then according to the position of the view assign the desired TextView 3 for the same
        TextView textView4 = currentItemView.findViewById(R.id.expectativaListViewPuchos);
        textView4.setText(String.valueOf(currentPuchoPosition.getExpectativa()));

        // then according to the position of the view assign the desired TextView 3 for the same
        TextView textView5 = currentItemView.findViewById(R.id.timeforeachListViewPuchos);
        textView5.setText(currentPuchoPosition.getTime_last());

        // then return the recyclable view
        return currentItemView;
    }
}
