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
import java.net.URL;
import java.net.URLEncoder;

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
                    StringBuilder urlBuilder = new StringBuilder("http://api.seibro.or.kr/openapi/service/StockSvc/getStkIsinByNmN1"); /*URL*/
                    urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=lG1p0ndDsxzYGHcM%2FQwNtO1vlTtBB3B00F9YTb%2F918rhSJ%2Flvtge6rSQAZULOdvwA9hTNGan%2BnasgZaolSCDtQ%3D%3D"); /*Service Key*/
                    urlBuilder.append("&" + URLEncoder.encode("secnNm","UTF-8") + "=" + URLEncoder.encode(s, "UTF-8")); /*번호별 회사명 관리*/
                    URL url = new URL(urlBuilder.toString());

                    new ApiTask().execute(url);

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