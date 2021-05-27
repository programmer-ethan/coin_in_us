package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.professionalandroid.apps.coin_in_us.R;



import java.io.IOException;

public class StockSearchFragment extends Fragment {

    private StockSearchViewModel stocksearchViewModel;
    private String TAG = "yaya";
    private LinearLayout tableLayout;
    private SearchView search;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stocksearchViewModel =
               new ViewModelProvider(this).get(StockSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stock_search, container, false);

        search = (SearchView) root.findViewById(R.id.stockSearch);
        tableLayout = (LinearLayout) root.findViewById(R.id.tableLayout);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 입력받은 문자열 처리
                try {
                    System.out.println(s);
                    ApiExplorer.searchApi(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });

        return root;
    }
}