package com.example.pucho;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
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

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.BDManager;
import com.example.pucho.controladores.NewExpectativaActivity;
import com.example.pucho.controladores.ControladorPuchos;
import com.example.pucho.controladores.TimeChangeReceiver;
import com.example.pucho.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private ListView listView;
    private Button btnAddPucho;
    private ImageButton newExpectativaBtn;
    private Switch switchNotifications;
    private TextView counterView, dateView;
    private SimpleCursorAdapter adapter;
    private ControladorPuchos puchos;
    private PuchoDia hoy;
    private Expectativas expectativa;
    private String formattedDateTime_current, formattedDateTime_saved;
    private BDManager dbManager;

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


        dbManager = new BDManager(this);
        formattedDateTime_current = getNowDate(getNowDateTime());


        counterView = findViewById(R.id.textView);
        dateView = findViewById(R.id.textView2);

        btnAddPucho = findViewById(R.id.btn_add);
        newExpectativaBtn = findViewById(R.id.imgbtn);
        switchNotifications = findViewById(R.id.notificationswitch);

        expectativa = checkExpectativasState(formattedDateTime_current);
        hoy = checkPuchoState(formattedDateTime_current);
        puchos = new ControladorPuchos(getApplicationContext(), expectativa, formattedDateTime_current);
        System.out.println("despues de checkState");
        System.out.println("Antes de establecer el ListView");
        setListView(expectativa);



        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    System.out.println("Switch is ON");
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 15);
                    calendar.set(Calendar.MILLISECOND, 0);
                    //calendar.setTimeInMillis(System.currentTimeMillis());
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(MainActivity.this, TimeChangeReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    //alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                    System.out.println("Alarm is set");
                    System.out.println(calendar.getTime());
                    createNotificationChannel();

                }else{
                    System.out.println("Switch is OFF");
                }
            }
        });

        btnAddPucho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPucho();
            }
        });

        newExpectativaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newExpectativa(getApplicationContext());
            }
        });

    }

    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name= "puchochannel";
        String desc = "Channel for Pucho App Manager";
        int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("puchochannelandroid", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
    private Expectativas checkExpectativasState(String date) {
        dbManager.open();

        Cursor cursor = dbManager.fetch_expectativas();
        Expectativas expectativas = null;
        if (cursor.getCount() > 0) {
            if (cursor.getInt(3) > 0) {
                expectativas = new Expectativas(cursor.getString(1));
                expectativas.setCantidad(cursor.getInt(2));
                expectativas.setDiasRestantes(cursor.getInt(3));
                expectativas.setEstado("PENDIENTE");

                System.out.println("despues de llamar al controlador ");
                dateView.setText(hoy.getFecha());

                System.out.println("despues de llamar al controlador y estableciendo la hora");
                counterView.setText(String.valueOf(hoy.getConsumo()));
            }
        }
        cursor.close();
        dbManager.close();
        return expectativas;
    }
    private PuchoDia checkPuchoState(String date){
        hoy = new ControladorPuchos(getApplicationContext(), expectativa, formattedDateTime_current).verifyNonExistence(date);
        return hoy;
    }

    private void addPucho(){
        hoy = puchos.addPucho();
        counterView.setText(String.valueOf(hoy.getConsumo()));
    }

    private void checkDayChanges(){
        try{
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDateTime_saved(String date){
         this.formattedDateTime_saved = date;
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
    private void setListView(Expectativas expectativas1){
        listView = findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.empty));

        System.out.println("Estableciendo ListWier en Main, controlador puchos");
        puchos = new ControladorPuchos(getApplicationContext(), expectativas1, formattedDateTime_current);

        System.out.println("llamando al getAdapter del controlador de puchos");
        adapter = puchos.getAdapter();

        System.out.println("Estableciendo adaptador al listview");
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