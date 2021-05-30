package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.MainActivity;
import com.professionalandroid.apps.coin_in_us.R;

public class CoinSearchFragment extends Fragment {
    //
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
    //
    private CoinSearchViewModel coinSearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coinSearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = root.findViewById(R.id.text_dashboard);
        final TextView textView2 = root.findViewById(R.id.text_dashboard2);
        final TextView textView3 = root.findViewById(R.id.text_dashboard3);
        /*
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
=======
public class CoinSearchFragment extends Fragment {

    private CoinSearchViewModel coinsearchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        coinsearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coin_search, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        coinsearchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
>>>>>>> origin/master:app/src/main/java/com/professionalandroid/apps/coin_in_us/ui/coinsearch/CoinSearchFragment.java
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.coinfo((String)textView.getText());

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.coinfo((String)textView2.getText());

            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.coinfo((String)textView3.getText());

            }
        });
        return root;
    }
}