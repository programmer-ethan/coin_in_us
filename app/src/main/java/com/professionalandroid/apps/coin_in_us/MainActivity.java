package com.professionalandroid.apps.coin_in_us;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        Button BTC = (Button) findViewById(R.id.BTC);
        Button XRP = (Button) findViewById(R.id.XRP);
        Button ETH = (Button) findViewById(R.id.ETH);
        Button back = (Button) findViewById(R.id.coin_back);
        Fragment fragment = new Coin_Info_Fragment();

        BTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "비트코인 BTC");
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, fragment).commit();
            }
        });
        XRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "리플 XRP");
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, fragment).commit();
            }
        });
        ETH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "이더리움 ETH");
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, fragment).commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });


    }

}