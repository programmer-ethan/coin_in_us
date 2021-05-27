package com.professionalandroid.apps.coin_in_us.ui.stocksearch;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;


public class ApiExplorer {
    public static void searchApi(String name) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.seibro.or.kr/openapi/service/StockSvc/getStkIsinByNmN1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=lG1p0ndDsxzYGHcM%2FQwNtO1vlTtBB3B00F9YTb%2F918rhSJ%2Flvtge6rSQAZULOdvwA9hTNGan%2BnasgZaolSCDtQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("secnNm","UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")); /*번호별 회사명 관리*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8")); /*페이지당 데이터 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}