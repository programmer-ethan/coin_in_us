package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.professionalandroid.apps.coin_in_us.MainActivity;
import com.professionalandroid.apps.coin_in_us.R;


import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class StockSearchFragment extends Fragment {

    private StockSearchViewModel stocksearchViewModel;
    private LinearLayout tableLayout;
    private SearchView search;
    private TextView textView;
    private String xml;
    private ApiTask apiExplorer;
    private static String[][] parsingData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stocksearchViewModel =
               new ViewModelProvider(this).get(StockSearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stock_search, container, false);

        search = (SearchView) root.findViewById(R.id.stockSearch);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 입력받은 문자열 처리
                try {
                    StringBuilder urlBuilder = new StringBuilder("http://api.seibro.or.kr/openapi/service/StockSvc/getStkIsinByNmN1"); /*URL*/
                    urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=lG1p0ndDsxzYGHcM%2FQwNtO1vlTtBB3B00F9YTb%2F918rhSJ%2Flvtge6rSQAZULOdvwA9hTNGan%2BnasgZaolSCDtQ%3D%3D"); /*Service Key*/
                    urlBuilder.append("&" + URLEncoder.encode("secnNm","UTF-8") + "=" + URLEncoder.encode(s, "UTF-8")); /*번호별 회사명 관리*/
                    URL url = new URL(urlBuilder.toString());

                    apiExplorer = new ApiTask();
                    xml = apiExplorer.execute(url).get();
                    parsingData = XmlParser.xmlparse(xml);

                    tableLayout = (LinearLayout) root.findViewById(R.id.linearLayout2);
                    TableRow tableRow = new TableRow(root.getContext());     // tablerow 생성
                    tableRow.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    for (int j = 0 ; j < parsingData.length; j++) {

                        Button star = new Button(root.getContext());

                        for(int i = 0 ; i < 3 ; i++) {
                            TextView textView = new TextView(root.getContext());
                            textView.setText(parsingData[j][i]);
                            textView.setGravity(Gravity.CENTER);
                            textView.setTextSize(18);
                            tableRow.addView(textView);
                        }
                        tableRow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) { stock(); }
                        });
                        tableLayout.addView(tableRow);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
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

    public String[][] getParsingData() {
        return parsingData;
    }

    public void stock() {
        Intent intent = new Intent(this, StockInfo.class);
        startActivity(intent);
    }

}