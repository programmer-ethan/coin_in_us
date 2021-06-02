package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.professionalandroid.apps.coin_in_us.R;

public class Loading extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //
        View returnView = inflater.inflate(R.layout.fragment_loading, container, false);
        return returnView;
    }
}