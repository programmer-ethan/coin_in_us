package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.MainActivity;
import com.professionalandroid.apps.coin_in_us.R;

public class StockSearchFragment extends Fragment {

    MainActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }


    private StockSearchViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(StockSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stock_search, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final Button stock_info = root.findViewById(R.id.stock_info);
        stock_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.stock();
            }
        });
        return root;
    }
}