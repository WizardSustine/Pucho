package com.example.pucho;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pucho.ENTIDADES.Expectativas;
import com.example.pucho.ENTIDADES.PuchoDia;
import com.example.pucho.SQLite.BDManager;
import com.example.pucho.SQLite.Contrato;
import com.example.pucho.controladores.ControladorExpectativa;
import com.example.pucho.controladores.ControladorPuchos;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnAdd;
    private ImageButton imgBtn;
    private TextView counterView, dateView;
    private SimpleCursorAdapter adapter;

    private Expectativas expectativas;
    private PuchoDia hoy;
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

        btnAdd = findViewById(R.id.btn_add);
        imgBtn = findViewById(R.id.imgbtn);

        expectativas = checkState(formattedDateTime_current);

    }

    private Expectativas checkState(String date){
        dbManager.open();

        Cursor cursor = dbManager.fetch_expectativas();
        if(cursor.getInt(3) > 0 ){
            Expectativas expectativas = new Expectativas(cursor.getString(1));
            expectativas.setCantidad(cursor.getInt(2));
            expectativas.setDiasRestantes(cursor.getInt(3));
            expectativas.setEstado("PENDIENTE");

            System.out.println("HOLA");
            ControladorPuchos puchos = new ControladorPuchos(getApplicationContext(), expectativas );
            hoy = puchos.verifyNonExistence(date);
            dateView.setText(hoy.getFecha());
        }else{
            newExpectativa(getApplicationContext());
        }
        cursor.close();
        dbManager.close();
        return expectativas;
    }

    private void addPucho(){
        ControladorPuchos puchos = new ControladorPuchos(getApplicationContext(), expectativas);
        hoy = puchos.addPucho();
        counterView.setText(String.valueOf(hoy.getCantidad()));
    }

    private void checkDayChanges(){
        try{
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean compareTime(String oldDay, String newDay){
        if(oldDay == newDay){
            return true;
        }else{
            setDateTime_saved(newDay);
            return false;
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
        Intent intent = new Intent(context, ControladorExpectativa.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("date", formattedDateTime_current);
        startActivity(intent);
    }
}