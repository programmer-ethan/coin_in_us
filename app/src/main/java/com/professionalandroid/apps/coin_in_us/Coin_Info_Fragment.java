package com.professionalandroid.apps.coin_in_us;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Coin_Info_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //
        Bundle coin_name = this.getArguments();
        String name = coin_name.getString("name");
        Bundle coin_detail = this.getArguments();
        String detail = coin_detail.getString("detail");
        //
        View returnView = inflater.inflate(R.layout.fragment_coin_info, container, false);
        TextView name_text = (TextView) returnView.findViewById(R.id.coin_name);
        name_text.setText("현재 종목 : " + name);
        TextView detail_text = (TextView) returnView.findViewById(R.id.coin_detail);
        detail_text.setText(detail);
        return returnView;
    }
}
