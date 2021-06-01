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
import com.professionalandroid.apps.coin_in_us.model.CoinModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private Gson mGson;

    List<CoinModel> items = new ArrayList<>();

    Request request;

    private final ThreadLocal<Callback<String>> mRetrofitCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            CoinListVO mCoinListVO = (CoinListVO) mGson.fromJson(result, CoinList.class);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

            t.printStackTrace();
        }
    };

    private TextView opening_price;
    private TextView fluctate_rate_24H;
    private TextView coinNm;
    private Retrofit mRetrofit;
    private Call<String> mCallCoinList;
    private RetrofitAPI mRetrofitAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opening_price = findViewById(R.id.opening_price);
        fluctate_rate_24H = findViewById(R.id.fluctate_rate_24H);
        coinNm = findViewById(R.id.coin_nm);

        setRetrofitInit();
        mCallCoinList();

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


    private void setRetrofitInit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.bithumb.com/public/ticker/ALL_KRW/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI mRetrofitAPI = mRetrofit.create(RetrofitAPI.class);
    }

    private void mCallCoinList() {
        mCallCoinList = mRetrofitAPI.getCoinList();
    }

    private Callback<String> RetrofitCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            Log.d(String.valueOf(this), result);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    };
    public interface RetrofitAPI {
        @GET("/coin.json")
        Call<String> getCoinList();
    }

    private void orderbookSelect(String coinNm)
    {
        final String coinName = coinNm;

        String url = "https://api.bithumb.com/public/ticker/BTC_KRW" + coinNm;
    }

    private void orderBookResponse(String response) {
        Gson gson = new Gson();
        CoinList coinInfo = gson.fromJson(response, CoinList.class);

        if(coinInfo.status.equals("0000"))
        {
            String openingPrice    = coinInfo.data.get("opening_price");
            String fluctateRate24H = coinInfo.data.get("fluctate_rate_24H");

            opening_price.setText(toDoubleFormat(Double.parseDouble(openingPrice))+"원");
            fluctate_rate_24H.setText(toDoubleFormat(Double.parseDouble(fluctateRate24H))+"%");
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
        public CoinListVO fromJson(String response, Class<CoinListVO> coinListClass) {

            return null;
        }
    }

    private class Request {
    }
}

 class CoinList {
    HashMap<String , String> data = new HashMap<String, String>();
    String status;
}



