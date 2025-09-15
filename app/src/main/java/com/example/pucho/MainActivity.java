package com.example.pucho;


import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pucho.ENTIDADES.Notificacion;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.BDManager;
import com.example.pucho.SQLite.Contrato;
import com.example.pucho.controladores.BDController;

public class MainActivity extends AppCompatActivity {
    private Notificacion notificacion;
    private ListView listView;
    private Button btnAddPucho;
    private ImageButton newExpectativaBtn;
    private Switch switchNotifications;
    private TextView counterView, dateView;
    private BDController bdController;
    private PuchoDia hoy;
    private String formattedDateTime_current;
    private boolean notificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
/*        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
*/

        formattedDateTime_current = getNowDate(getNowDateTime());

        bdController = new BDController(this, formattedDateTime_current);
        hoy = bdController.getPuchoDia();

        counterView = findViewById(R.id.textView);
        dateView = findViewById(R.id.textView2);
        dateView.setText(formattedDateTime_current);
        listView = findViewById(R.id.listView);

        counterView.setText(String.valueOf(hoy.getConsumo()));

        btnAddPucho = findViewById(R.id.btn_add);
        newExpectativaBtn = findViewById(R.id.imgbtn);
        switchNotifications = findViewById(R.id.notificationswitch);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean savedState = preferences.getBoolean("switch_state", false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
        notificationSwitch = savedState;

        System.out.println("Antes de establecer el ListView");
        listView.setEmptyView(findViewById(R.id.empty));
        setListView();

        notificacion = new Notificacion(this);

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    System.out.println("Switch is ON");
                    notificationSwitch = true;

                    editor.putBoolean("switch_state", true); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    if(hoy.getExpectativa() > hoy.getConsumo() && hoy.getConsumo() > 0){
                        notificacion.setNotification(setTimeNextPucho(), notificationSwitch);
                    }
                }else{
                    System.out.println("Switch is OFF");
                    notificationSwitch = false;

                    editor.putBoolean("switch_state", false); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    notificacion.setNotification(0, notificationSwitch);
                }
            }
        });

        btnAddPucho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formattedDateTime_current = getNowDate(getNowDateTime());
                hoy = bdController.addPuchos(formattedDateTime_current);
                setListView();

                if(notificationSwitch) {
                    notificacion.setNotification(setTimeNextPucho(), notificationSwitch);
                }else{
                    System.out.println("notificationSwitch boolean es false");
                }

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
        formattedDateTime_current = getNowDate(getNowDateTime());
        hoy = bdController.setExpectativa();
        setListView();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean savedState = preferences.getBoolean("switch_state", false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
    }

    private Date getNowDateTime(){
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        return currentTime;
    }
    private String getNowDate(Date date){
        // Format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(date);
        return currentDate;
    }
    private void newExpectativa(Context context){
        Intent intent = new Intent(context, NewExpectativaActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("date", formattedDateTime_current);
        startActivity(intent);
    }
    private void setListView(){
        System.out.println("Set List View en Main");
        SimpleCursorAdapter adapter = bdController.getPuchosAdapter();
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
    private long setTimeNextPucho(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        long millis = 0;
        try {
            if(hoy.getExpectativa() > hoy.getConsumo()) {
                int cantidadRestante = hoy.getExpectativa() - hoy.getConsumo();

                Date timeFinish = timeFormat.parse(formattedDateTime_current + " 23:59:59");

                Date timeLast = timeFormat.parse(formattedDateTime_current + " " + hoy.getTime_last());
                System.out.println(hoy.getTime_last() + " esta es la hora de guardado del pucho");
                System.out.println(timeFinish.getTime() + " esta es la hora de finalización del día");
                System.out.println(timeLast.getTime() + " esta es la hora de guardado del pucho en método");

                long diferencia = timeFinish.getTime() - timeLast.getTime();

                System.out.println(diferencia + " esto sería la diferencia en millis?");
                long timeLapse = diferencia / cantidadRestante;

                millis = timeLapse + timeLast.getTime();
            }
            String pruebasss = timeFormat.format(millis);
            System.out.println(pruebasss + " se supone que suene a esta hora");
        }catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return millis;
    }
}