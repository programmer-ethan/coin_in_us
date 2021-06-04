package com.professionalandroid.apps.coin_in_us.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.R;

public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_interested, container, false);//false

        Context context = getActivity();
        SharedPreferences pref
                = context.getSharedPreferences("MYPREFERENCE", Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        String stock_name1 = pref.getString("STOCK_NAME_1", "");
        String stock_name_en1 = pref.getString("STOCK_EN_NAME_1", "");
        String stock_kind1 = pref.getString("STOCK_KIND_1", "");

        String coin_name1 = pref.getString("COIN_NAME_1", "");
        String coin_price1 = pref.getString("COIN_PRICE_1", "");
        String coin_rate1 = pref.getString("COIN_RATE_1", "");


//동적 테이블
        TableLayout tableLayout, tableLayout2;
//stock
        tableLayout = root.findViewById(R.id.stocktable);
        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i=0;i<3;i++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            if(i==0) {
                textView.setText(stock_name1);
                textView.setMaxWidth(140);
            }
            else if(i==1) {
                textView.setText(stock_name_en1);
                textView.setMaxWidth(100);
            }
            else {
                textView.setText(stock_kind1);
                textView.setMaxWidth(60);
            }
            textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.table_inside);
            tableRow.setBackgroundResource(R.drawable.table_inside);
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
//coin
        tableLayout2 = root.findViewById(R.id.cointable);
        TableRow tableRow2 = new TableRow(context);
        tableRow2.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (int i=0;i<3;i++){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            if(i==0) textView.setText(coin_name1);
            else if(i==1) textView.setText(coin_price1);
            else textView.setText(coin_rate1);
            textView.setTextSize(18);
            textView.setBackgroundResource(R.drawable.table_inside);
            tableRow2.addView(textView);
        }
        tableLayout2.addView(tableRow2);
        return root;
    }
}