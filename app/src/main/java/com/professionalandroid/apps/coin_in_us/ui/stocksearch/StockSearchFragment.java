package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

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

public class StockSearchFragment extends Fragment {

    private StockSearchViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(StockSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stock_search, container, false);

        return root;
    }
}