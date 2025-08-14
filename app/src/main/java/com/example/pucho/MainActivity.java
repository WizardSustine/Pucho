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
import android.widget.CursorAdapter;
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
import com.example.pucho.SQLite.Contrato;
import com.example.pucho.controladores.BDController;
import com.example.pucho.controladores.ControladorPuchos;
import com.example.pucho.controladores.TimeChangeReceiver;

public class MainActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private ListView listView;
    private Button btnAddPucho;
    private ImageButton newExpectativaBtn;
    private Switch switchNotifications;
    private TextView counterView, dateView;
    private BDController bdController;
    private SimpleCursorAdapter adapter;
    private PuchoDia hoy;
    private String formattedDateTime_current;

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

        counterView = findViewById(R.id.textView);
        dateView = findViewById(R.id.textView2);
        dateView.setText(formattedDateTime_current);
        listView = findViewById(R.id.listView);

        btnAddPucho = findViewById(R.id.btn_add);
        newExpectativaBtn = findViewById(R.id.imgbtn);
        switchNotifications = findViewById(R.id.notificationswitch);

        System.out.println("Antes de establecer el ListView");
        listView.setEmptyView(findViewById(R.id.empty));
        setListView();

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 15);
                calendar.set(Calendar.MILLISECOND, 0);
                //calendar.setTimeInMillis(System.currentTimeMillis());
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, TimeChangeReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                if(isChecked){
                    System.out.println("Switch is ON");

                    //alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                    System.out.println("Alarm is set");
                    System.out.println(calendar.getTime());
                    createNotificationChannel();

                }else{
                    System.out.println("Switch is OFF");
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        btnAddPucho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoy = bdController.addPuchos(formattedDateTime_current);
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

        bdController.setExpectativa();
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
        BDManager bdManager = new BDManager(this);
        bdManager.open();
        final String[] from = new String[]{
                Contrato.ENTRADAS._ID, Contrato.ENTRADAS.COLUMNA_FECHA, Contrato.ENTRADAS.COLUMNA_CANTIDAD, Contrato.ENTRADAS.COLUMNA_EXPECTATIVA, Contrato.ENTRADAS.COLUMNA_TIME_LAST, Contrato.ENTRADAS.COLUMNA_ESTADO
        };
        final int[] to = new int[]{R.id.id_ListViewPuchos, R.id.dateListViewPuchos, R.id.cantidadListViewPuchos, R.id.expectativaListViewPuchos,R.id.timeforeachListViewPuchos, R.id.stateListViewPuchos};                bdManager.open();
        Cursor cursor = bdManager.fetch_puchos();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.view_consumo, cursor, from, to, 0);
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