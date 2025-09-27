package com.example.pucho;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.controladores.AlarmAndBDController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static ListView listView;
    private static Button btnAddPucho;
    private static ImageButton newExpectativaBtn;
    private static Switch switchNotifications;
    private static TextView counterView, dateView;
    private PuchoDia hoy;
    private String formattedDate;
    private AlarmAndBDController alarmAndBDController;
    public static boolean notificationSwitch;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        Date now = new Date();
        formattedDate = dateFormat.format(now);;

        alarmAndBDController = new AlarmAndBDController(this);

        hoy = alarmAndBDController.getPucho();

        counterView = findViewById(R.id.textView);
        dateView = findViewById(R.id.textView2);
        dateView.setText(formattedDate);
        listView = findViewById(R.id.listView);

        counterView.setText(String.valueOf(hoy.getConsumo()));

        btnAddPucho = findViewById(R.id.btn_add);
        newExpectativaBtn = findViewById(R.id.imgbtn);
        switchNotifications = findViewById(R.id.notificationswitch);

        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        boolean savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
        notificationSwitch = savedState;

        listView.setEmptyView(findViewById(R.id.empty));
        setListView();



        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    System.out.println("Switch is ON");
                    notificationSwitch = true;

                    editor.putBoolean(ContratoApp.SWITCH_STATE, true); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    if(hoy.getExpectativa() > hoy.getConsumo() && hoy.getConsumo() > 0){
                        alarmAndBDController.setAlarmEvent(1);
                    }
                }else{
                    System.out.println("Switch is OFF");
                    notificationSwitch = false;

                    editor.putBoolean("switch_state", false); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    alarmAndBDController.setAlarmEvent(0);
                }
            }
        });

        btnAddPucho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmAndBDController.addPucho();
                setListView();
                counterView.setText(String.valueOf(hoy.getConsumo()));
            }
        });

        newExpectativaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExpectativa(getApplicationContext());
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Date now = new Date();
        formattedDate = dateFormat.format(now);;
        hoy = alarmAndBDController.setExpectativas();
        counterView.setText(String.valueOf(hoy.getConsumo()));
        setListView();

        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        boolean savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
        if (intent.getIntExtra(ContratoApp.CANCELAR, 0) == 3) {
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Date now = new Date();
        formattedDate = dateFormat.format(now);;
        hoy = alarmAndBDController.setExpectativas();
        counterView.setText(String.valueOf(hoy.getConsumo()));
        setListView();

        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        boolean savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
    }
    private void newExpectativa(Context context){
        System.out.println(ContratoApp.DATE + " " + formattedDate);
        Intent intent = new Intent(context, NewExpectativaActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ContratoApp.DATE, formattedDate);
        startActivity(intent);
    }

    private void setListView(){
        System.out.println("Set List View en Main");
        SimpleCursorAdapter adapter = alarmAndBDController.getAdapter();
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        System.out.println("Estableciendo ListWie on item click listener");
        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

            }
        });
    }

}