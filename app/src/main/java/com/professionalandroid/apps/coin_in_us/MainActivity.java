package com.professionalandroid.apps.coin_in_us;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView opening_price;
    private TextView fluctate_rate_24H;
    private TextView coinNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opening_price        = findViewById(R.id.opening_price);
        fluctate_rate_24H    = findViewById(R.id.fluctate_rate_24H);
        coinNm               = findViewById(R.id.coin_nm);

        SearchView searchView = findViewById(R.id.search_View);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm = coinNm.getText().toString();

                orderbookSelect(nm.toUpperCase());
            }
        });

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

    private void orderbookSelect(String coinNm)
    {
        final String coinName = coinNm;

        String url = "https://api.bithumb.com/public/ticker/{ALL}_{KRW}" + coinNm;
    }

    private void orderBookResponse(String response) {
        Gson gson = new Gson();
        CoinList coinInfo = gson.fromJson(response, CoinList.class);

        if(coinInfo.status.equals("0000"))
        {
            String openingPrice    = coinInfo.data.get("opening_price");
            String fluctateRate24h = coinInfo.data.get("fluctate_rate_24H");

            opening_price.setText(toDoubleFormat(Double.parseDouble(openingPrice))+"원");
            fluctate_rate_24H.setText(toDoubleFormat(Double.parseDouble(fluctateRate24h))+"%");
        }
    }

    private void println( String data)
    {
        Log.d("MainActivity" , data);
    }

    private String toDoubleFormat(Double num) {
        DecimalFormat df = null;

        if (num >= 100 && num <= 999.9){
            df = new DecimalFormat("000.0");
        }else if(num >= 10 && num <= 99.99){
            df = new DecimalFormat("00.00");
        }else if (num >= 1 && num <= 9.9999){
            df = new DecimalFormat("0.0000");
        }else{
            df = new DecimalFormat("###,###,###");
        }
        return df.format(num);
    }


    private class Gson {
        public CoinList fromJson(String response, Class<CoinList> coinListClass) {

            return null;
        }
    }
}
 class CoinList {
    HashMap<String , String> data = new HashMap<String, String>();
    String status;
}



