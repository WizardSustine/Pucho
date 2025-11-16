package com.example.pucho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pucho.ViewGroups.CollectionAdapterApp;
import com.example.pucho.ViewGroups.GraphFragment;
import com.example.pucho.ViewGroups.ListFragment;
import com.example.pucho.ViewGroups.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static Fragment listFragment, graphFragment;
    public static CollectionAdapterApp viewPagerAdapter;
    public static ViewPager2 viewPager2;
    private static Button btnAddPucho;
    private static ImageButton newExpectativaBtn;
    private static Switch switchNotifications;
    private static TextView counterView, dateView;
    private String formattedDate;
    public static boolean savedState;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date now = new Date();
        formattedDate = dateFormat.format(now);;

        //Acá está el código para el ViewPager2
        viewPager2 = findViewById(R.id.pager);
        viewPagerAdapter = new CollectionAdapterApp(getSupportFragmentManager(),getLifecycle());

        //alarmAndBDController = new AlarmAndBDController(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        listFragment = new ListFragment();

        graphFragment = new GraphFragment();

        //Acá es para añadir fragmentos al viewPager. Agrego un fragmento con lista diaria y otro con la misma data en grafico lineal
        viewPagerAdapter.addFragment(listFragment);
        viewPagerAdapter.addFragment(graphFragment);
        viewPager2.setAdapter(viewPagerAdapter);

        //Un tab para que tenga algún señalador y se note que se puede cambiar la vista
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        //tab.setText("Grafico");
                        break;
                }
            }
        }).attach();


        counterView = findViewById(R.id.textView);
        dateView = findViewById(R.id.textView2);

        btnAddPucho = findViewById(R.id.btn_add);
        newExpectativaBtn = findViewById(R.id.imgbtn);
        switchNotifications = findViewById(R.id.notificationswitch);

        dateView.setText(formattedDate);
        counterView.setText(String.valueOf(viewModel.getConsumo()));

        //Guardo el estado del switch
        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    /*if(!alarmAndBDController.setNotificationPermission()){

                    };*/
                    System.out.println("Switch is ON");
                    savedState = true;

                    editor.putBoolean(ContratoApp.SWITCH_STATE, true); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    if(viewModel.getExpectativas() > viewModel.getConsumo() && viewModel.getConsumo() > 0){
                        viewModel.setAlarm(1);
                    }
                }else{
                    System.out.println("Switch is OFF");
                    savedState = false;

                    editor.putBoolean("switch_state", false); // "switch_state" is the key
                    editor.apply(); // Apply the changes asynchronously

                    viewModel.setAlarm(0);
                }
            }
        });

        btnAddPucho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.closeNotification();
                viewModel.addPucho();
                counterView.setText(String.valueOf(viewModel.getConsumo()));
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
        formattedDate = dateFormat.format(now);
        viewModel.setExpectativas();
        counterView.setText(String.valueOf(viewModel.getConsumo()));

        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        boolean savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
        if (intent.getIntExtra(ContratoApp.CANCELAR, 0) == 3) {
            System.out.println("CANCELAR NOTIFICATION AHORA POR FAVOR");
            viewModel.closeNotification();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date now = new Date();
        formattedDate = dateFormat.format(now);;
        viewModel.setExpectativas();
        counterView.setText(String.valueOf(viewModel.getConsumo()));

        SharedPreferences preferences = getSharedPreferences(ContratoApp.MYPREFS, MODE_PRIVATE);
        boolean savedState = preferences.getBoolean(ContratoApp.SWITCH_STATE, false); // false is the default value if no state is found
        switchNotifications.setChecked(savedState);
    }

    //Llamado a la actividad para agregar una meta
    private void newExpectativa(Context context){
        System.out.println(ContratoApp.DATE + " " + formattedDate);
        Intent intent = new Intent(context, NewExpectativaActivity.class);//.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ContratoApp.DATE, formattedDate);
        startActivity(intent);
    }
}