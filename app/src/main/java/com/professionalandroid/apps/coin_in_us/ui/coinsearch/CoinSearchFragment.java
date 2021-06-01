package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.MainActivity;
import com.professionalandroid.apps.coin_in_us.R;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class CoinSearchFragment extends Fragment {

    private CoinSearchViewModel coinsearchViewModel;
    private LinearLayout tableLayout;
    private SearchView search;
    private TextView textView;
    private String xml;
    private static String[][] parsingData;
    float pixels;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coinsearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coin_search, container, false);

        search = (SearchView) root.findViewById(R.id.coinSearch);


        final TextView textView = root.findViewById(R.id.textView);
        coinsearchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}