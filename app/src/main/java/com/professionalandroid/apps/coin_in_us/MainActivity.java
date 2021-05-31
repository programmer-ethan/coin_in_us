package com.professionalandroid.apps.coin_in_us;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ServiceConfigurationError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_coin, R.id.navigation_home, R.id.navigation_stock)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void writeSharedPreference(View view){
//        EditText txtValue = (EditText) findViewById(R.id.saved_data);
//        String value = txtValue.getText().toString();
        // 1. get Shared Preference
        SharedPreferences sharedPreference
                = this.getSharedPreferences("MYPREFRENCE", Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
        // 2. get Editor
        SharedPreferences.Editor editor = sharedPreference.edit();
        // 3. set Key values
        editor.putString("MYKEY","VALUE1");
        editor.putString("KEY2", "VALUE2");
        // 4. commit the values
        editor.commit();
    }
}
