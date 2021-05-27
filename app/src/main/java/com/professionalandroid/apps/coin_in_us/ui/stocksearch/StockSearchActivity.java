package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.professionalandroid.apps.coin_in_us.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import android.util.Log;


public class StockSearchActivity extends AppCompatActivity {

    private static final String TAG = "StockSearchActivity";
    private LinearLayout tableLayout;
    private SearchView search;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stock_search);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_coin, R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        search = findViewById(R.id.stockSearch);
        tableLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 입력받은 문자열 처리
                textView.setText("오류 없음");
                Log.i(TAG, textView.getText().toString());
                try {
                    ApiExplorer.searchApi(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });


        TableRow tableRow = new TableRow(this);     // tablerow 생성
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(36);
            tableRow.addView(textView);        // tableRow에 view 추가
        }
        tableLayout.addView(tableRow);        // tableLayout에 tableRow 추가

    }
}