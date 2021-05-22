package com.professionalandroid.apps.coin_in_us;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Coin_Info_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //
        Bundle coin_name = this.getArguments();
        String name = coin_name.getString("name");
        Bundle coin_detail = this.getArguments();
        String detail = coin_detail.getString("detail");
        Bundle coin_current = this.getArguments();
        String current = coin_current.getString("current");
        Bundle coin_rate = this.getArguments();
        double rate = coin_rate.getDouble("rate");
        //
        View returnView = inflater.inflate(R.layout.fragment_coin_info, container, false);
        TextView name_text = (TextView) returnView.findViewById(R.id.coin_name);
        name_text.setText("현재 종목 : " + name);
        TextView detail_text = (TextView) returnView.findViewById(R.id.coin_detail);
        detail_text.setText(detail);
        TextView current_text = (TextView) returnView.findViewById(R.id.coin_current);
        current_text.setText(current);
        if (rate < 0) {
            current_text.setTextColor(Color.rgb(50, 50, 255));
            current_text.append("▼" + rate + "%");
        }
        else {
            current_text.setTextColor(Color.rgb(255, 50, 50));
            current_text.append("▲" + rate + "%");
        }
        //
        Button button4 = (Button)returnView.findViewById(R.id.close_button);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(Coin_Info_Fragment.this).commit();
            }
        });
        return returnView;
    }
}
