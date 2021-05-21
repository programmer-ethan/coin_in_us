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
        Bundle extra = this.getArguments();
        String name = extra.getString("name");
        View returnView = inflater.inflate(R.layout.fragment_coin_info, container, false);
        TextView txtOne = (TextView) returnView.findViewById(R.id.coin_name);
        txtOne.setText(name);
        return returnView;
    }
}
