package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.R;

public class CoinSearchFragment extends Fragment {
    private CoinSearchViewModel coinSearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coinSearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coin_search, container, false);
        return root;
    }
}