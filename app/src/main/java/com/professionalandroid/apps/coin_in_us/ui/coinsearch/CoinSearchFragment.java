package com.professionalandroid.apps.coin_in_us.ui.coinsearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.MainActivity;
import com.professionalandroid.apps.coin_in_us.R;

public class CoinSearchFragment extends Fragment {
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
    private CoinSearchViewModel coinSearchViewModel;
    //
    private Context context;
    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = container.getContext();

        coinSearchViewModel =
                new ViewModelProvider(this).get(CoinSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coin_search, container, false);

        SearchView coinSearch = (SearchView) root.findViewById(R.id.coinSearch);
        TableLayout table = (TableLayout) root.findViewById(R.id.cointable);

        coinSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, "검색 처리됨 : " + query, Toast.LENGTH_SHORT).show();
                activity.coin_search_load(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return root;
    }
}