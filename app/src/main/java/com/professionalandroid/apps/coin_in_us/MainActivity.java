package com.professionalandroid.apps.coin_in_us;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //
    String urlStr;
    Handler handler = new Handler();
    String merge_detail;
    String current_price;
    double rate;
    Fragment fragment = new Coin_Info_Fragment();
    //
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
    }
    //
    public void coinfo(String name) {
        //
        String coin_name = name;
        urlStr = "https://api.bithumb.com/public/ticker/"+coin_name+"_KRW";
        RequestThread order_thread = new RequestThread();
        order_thread.start();
        //
        handler.postDelayed(new Runnable()  {
            public void run() {
                urlStr = "https://api.bithumb.com/public/transaction_history/"+coin_name+"_KRW";
                RequestThread ticker_thread = new RequestThread();
                ticker_thread.start();
            }
        }, 300);
        //
        handler.postDelayed(new Runnable()  {
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("detail", merge_detail);
                bundle.putString("current", current_price);
                bundle.putDouble("rate", rate);
                fragment.setArguments(bundle);
                //
                getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, fragment).commit();
            }
        }, 400);
    }
    //
    class RequestThread extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String api = null;
                        if(urlStr.contains("ticker")) {
                            while(true){
                                api = reader.readLine();
                                if(api == null)
                                    break;
                                NumberFormat nf = NumberFormat.getInstance();
                                JSONObject entire_text = new JSONObject(api);
                                String data = entire_text.getString("data");
                                JSONObject data_json = new JSONObject(data);
                                String opening_price = nf.format(data_json.getDouble("opening_price"));
                                String closing_price = nf.format(data_json.getDouble("closing_price"));
                                String min_price = nf.format(data_json.getDouble("min_price"));
                                String max_price = nf.format(data_json.getDouble("max_price"));
                                String units_traded = nf.format(data_json.getDouble("units_traded"));
                                String acc_trade_value = nf.format(data_json.getDouble("acc_trade_value"));
                                String prev_closing_price = nf.format(data_json.getDouble("prev_closing_price"));
                                String units_traded_24H = nf.format(data_json.getDouble("units_traded_24H"));
                                String acc_trade_value_24H = nf.format(data_json.getDouble("acc_trade_value_24H"));
                                String fluctate_24H = nf.format(data_json.getDouble("fluctate_24H"));
                                rate = data_json.getDouble("fluctate_rate_24H");
                                String fluctate_rate_24H = nf.format(rate);
                                long date = data_json.getLong("date");
                                Date date_t = new Date(date);
                                SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 a hh시 mm분 ss초");
                                String tt = sdf.format(date_t);
                                merge_detail = "--00시 기준--" + "\n시가 : " + opening_price
                                        + "\n종가 : " + closing_price + "\n저가 : " + min_price
                                        + "\n고가 : " + max_price + "\n거래량 : " + units_traded
                                        + "\n거래금액 :" + acc_trade_value + "\n\n전일종가 : " + prev_closing_price
                                        + "\n\n--24시간 기준--" + "\n거래량 : " + units_traded_24H
                                        + "\n거래금액 : " + acc_trade_value_24H + "\n변동가 : " + fluctate_24H
                                        + "\n변동률 : " + fluctate_rate_24H + "%" + "\n\n현재시간 : \n" + tt;
                            }
                        }
                        else {
                            while(true) {
                                api = reader.readLine();
                                if (api == null)
                                    break;
                                JSONObject entire_text = new JSONObject(api);
                                JSONArray jsonArray = entire_text.getJSONArray("data");
                                String cur = NumberFormat.getInstance().format(jsonArray.getJSONObject(19).getDouble("price"));
                                current_price = cur;
                            }
                        }
                        reader.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}