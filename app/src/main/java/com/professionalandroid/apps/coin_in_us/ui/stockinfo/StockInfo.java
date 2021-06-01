package com.professionalandroid.apps.coin_in_us.ui.stockinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.professionalandroid.apps.coin_in_us.R;

import org.jetbrains.annotations.NotNull;

public class StockInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);
        Intent stockInfoData = getIntent();
        String[] data = stockInfoData.getStringArrayExtra("data");
        System.out.print(data);

        TextView tmp = (TextView) findViewById(R.id.one);
        tmp.setText(data[0]);

        tmp = (TextView) findViewById(R.id.two);
        tmp.setText(data[1]);

        tmp = (TextView) findViewById(R.id.three);
        tmp.setText(data[2]);

        tmp = (TextView) findViewById(R.id.four);
        tmp.setText(data[3]);

        tmp = (TextView) findViewById(R.id.five);
        tmp.setText(data[4]);

        tmp = (TextView) findViewById(R.id.six);
        tmp.setText(data[5]);

        tmp = (TextView) findViewById(R.id.seven);
        tmp.setText(data[6]);

    }


}
