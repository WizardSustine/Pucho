package com.example.pucho.ViewGroups;

import android.app.Application;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.controladores.AlarmAndBDController;

import java.util.ArrayList;

/** ViewModel principal que gestiona la lógica de negocio y los datos para la UI.
 * Mantiene la comunicación entre la UI y el controlador de datos (AlarmAndBDController).*/
public class MainViewModel extends AndroidViewModel {
    // El controlador para la base de datos y las alarmas.
    private static AlarmAndBDController alarmAndBDController;

    // LiveData para la lista de días a mostrar en la vista principal (ListView/RecyclerView).
    private static MutableLiveData<ArrayList<PuchoDia>> model = new MutableLiveData<>();

    // LiveData para los datos del gráfico (últimos 30 días).
    private static MutableLiveData<ArrayList<PuchoDia>> graphModel = new MutableLiveData<>();
    public LiveData<ArrayList<PuchoDia>> getModel(){
        return model;
    }
    public LiveData<ArrayList<PuchoDia>> getGraphModel() { return graphModel;}
    public MainViewModel(Application application) {
        super(application);

        // Inicializa el controlador al crear el ViewModel.
        alarmAndBDController = new AlarmAndBDController(getApplication().getApplicationContext());
        setModel();
        setGraphModel();
    }
    public void setGraphModel(){
        graphModel.setValue( alarmAndBDController.get30Dias());}
    public void setModel(){
        model.setValue( alarmAndBDController.getAdapter());}
    public void updatePuchoDia() {
        model.setValue(alarmAndBDController.getAdapter()); // Notify observers of the change
    }
    public void updateGraphModel(){
        graphModel.setValue(alarmAndBDController.get30Dias());
    }
    public PuchoDia getHoy() {
        return alarmAndBDController.getPucho();
    }
    public int getConsumo(){
        return getHoy().getConsumo();
    }
    public int getExpectativas(){
        return getHoy().getExpectativa();
    }
    public void setAlarm(int i) {
        alarmAndBDController.setAlarmEvent(i);
    }
    public void closeNotification(){
        alarmAndBDController.closeNotification();
    }
    public void addPucho(){
        alarmAndBDController.addPucho();
        updatePuchoDia();
        updateGraphModel();
    }
    public void delete_pucho(long id){
        alarmAndBDController.delete_pucho(id);
        updatePuchoDia();
        updateGraphModel();
    }
    public void setExpectativas(){
        alarmAndBDController.setExpectativas();
    }
}
