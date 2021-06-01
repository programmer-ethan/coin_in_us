package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.R;

public class CoinSearchFragment extends Fragment {

    private CoinSearchViewModel coinsearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coinsearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coin_search, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        coinsearchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}