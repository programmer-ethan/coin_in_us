package com.professionalandroid.apps.coin_in_us;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.professionalandroid.apps.coin_in_us.ui.coininfo.Coin_Info_Fragment;
import com.professionalandroid.apps.coin_in_us.ui.coinsearch.Loading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    //
    String urlStr;
    Handler handler = new Handler();
    String merge_detail;
    String current_price;
    double rate;
    Fragment fragment = new Coin_Info_Fragment();
    Fragment loadscreen = new Loading();
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_coin, R.id.navigation_home, R.id.navigation_stock)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void writeSharedPreference(View view){
//        EditText txtValue = (EditText) findViewById(R.id.saved_data);
//        String value = txtValue.getText().toString();
        // 1. get Shared Preference
        SharedPreferences sharedPreference
                = this.getSharedPreferences("MYPREFRENCE", Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
        // 2. get Editor
        SharedPreferences.Editor editor = sharedPreference.edit();
        // 3. set Key values
        editor.putString("MYKEY","VALUE1");
        editor.putString("KEY2", "VALUE2");
        // 4. commit the values
        editor.commit();
    }
    //
    public void coin_search_load(String coin){
        //
        String[] forprint = new String[163];
        int count = 0;
        int k = 0;
        for(int i = 0 ; i < 163 ; i++){
            if(kor_coin_arr[i].contains(coin)){
                forprint[k] = kor_coin_arr[i];
                count++;
                k++;
            }
        }
        if(count > 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, loadscreen).commit();
        }
        TableLayout tableLayout = (TableLayout)findViewById(R.id.cointableLayout);

        int row = tableLayout.getChildCount();

        if(row>1) {
            for(int i = 0 ; i < row-1 ; i++) {
                tableLayout.removeViewAt(1);
            }
        }
        for(int j = 0 ; j < count ; j++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < 4; i++) {
                TextView textView = new TextView(this);
                if(i == 0){
                    textView.setText("★");
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(20);
                }
                else if(i == 1) {
                    textView.setText(forprint[j]);
                }
                else if(i == 2){
                    int finalJ = j;
                    handler.postDelayed(new Runnable()  {
                        public void run() {
                            urlStr = "https://api.bithumb.com/public/transaction_history/"+kortosymbol(forprint[finalJ])+"_KRW";
                            RequestThread order_thread = new RequestThread();
                            order_thread.start();
                        }
                    }, 700 * j);
                    handler.postDelayed(new Runnable()  {
                        public void run() {
                            textView.setText(String.valueOf(current_price));
                            textView.setGravity(Gravity.CENTER);
                        }
                    }, 700 * j + 700);
                }
                else if(i == 3){
                    int finalJ = j;
                    handler.postDelayed(new Runnable()  {
                        public void run() {
                            urlStr = "https://api.bithumb.com/public/ticker/"+kortosymbol(forprint[finalJ])+"_KRW";
                            RequestThread ticker_thread = new RequestThread();
                            ticker_thread.start();
                        }
                    }, 700 * j + 300);
                    int finalCount = count;
                    handler.postDelayed(new Runnable()  {
                        public void run() {
                            textView.setText(String.valueOf(rate));
                            textView.setGravity(Gravity.CENTER);
                            if(rate<0) {
                                textView.setTextColor(Color.rgb(50, 50, 255));
                            }
                            else{
                                textView.setTextColor(Color.rgb(255, 50, 50));
                            }
                            if(finalJ== finalCount -1){
                                getSupportFragmentManager().beginTransaction().remove(loadscreen).commit();
                            }
                        }
                    }, 700 * j + 700);
                }
                if(j % 2 == 0) {
                    tableRow.setBackgroundColor(Color.rgb(200, 200, 200));
                }
                tableRow.setId(j);
                final String coin_load = forprint[j];
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coinfo(kortosymbol(coin_load));
                    }
                });
                tableRow.addView(textView);
                tableRow.setPadding(0,0,0,10);
            }
            tableLayout.addView(tableRow);
        }
    }
    //
    public void coinfo(String name) {
        //
        String kor_name = symboltokor(name);
        urlStr = "https://api.bithumb.com/public/ticker/"+name+"_KRW";
        RequestThread ticker_thread = new RequestThread();
        ticker_thread.start();
        //
        handler.postDelayed(new Runnable()  {
            public void run() {
                urlStr = "https://api.bithumb.com/public/transaction_history/"+name+"_KRW";
                RequestThread order_thread = new RequestThread();
                order_thread.start();
            }
        }, 300);
        //
        handler.postDelayed(new Runnable()  {
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("name", kor_name + " " + name);
                bundle.putString("detail", merge_detail);
                bundle.putString("current", current_price);
                bundle.putDouble("rate", rate);
                fragment.setArguments(bundle);
                //
                getSupportFragmentManager().beginTransaction().replace(R.id.coin_info_container, fragment).commit();
            }
        }, 400);
    }
    //
    class RequestThread extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String api = null;
                        if(urlStr.contains("ticker")) {
                            while(true){
                                api = reader.readLine();
                                if(api == null)
                                    break;
                                NumberFormat nf = NumberFormat.getInstance();
                                JSONObject entire_text = new JSONObject(api);
                                String data = entire_text.getString("data");
                                JSONObject data_json = new JSONObject(data);
                                String opening_price = nf.format(data_json.getDouble("opening_price"));
                                String closing_price = nf.format(data_json.getDouble("closing_price"));
                                String min_price = nf.format(data_json.getDouble("min_price"));
                                String max_price = nf.format(data_json.getDouble("max_price"));
                                String units_traded = nf.format(data_json.getDouble("units_traded"));
                                String acc_trade_value = nf.format(data_json.getDouble("acc_trade_value"));
                                String prev_closing_price = nf.format(data_json.getDouble("prev_closing_price"));
                                String units_traded_24H = nf.format(data_json.getDouble("units_traded_24H"));
                                String acc_trade_value_24H = nf.format(data_json.getDouble("acc_trade_value_24H"));
                                String fluctate_24H = nf.format(data_json.getDouble("fluctate_24H"));
                                rate = data_json.getDouble("fluctate_rate_24H");
                                String fluctate_rate_24H = nf.format(rate);
                                long date = data_json.getLong("date");
                                Date date_t = new Date(date);
                                SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 a hh시 mm분 ss초");
                                String tt = sdf.format(date_t);
                                merge_detail = "--00시 기준--" + "\n시가 : " + opening_price
                                        + "\n종가 : " + closing_price + "\n저가 : " + min_price
                                        + "\n고가 : " + max_price + "\n거래량 : " + units_traded
                                        + "\n거래금액 :" + acc_trade_value + "\n\n전일종가 : " + prev_closing_price
                                        + "\n\n--24시간 기준--" + "\n거래량 : " + units_traded_24H
                                        + "\n거래금액 : " + acc_trade_value_24H + "\n변동가 : " + fluctate_24H
                                        + "\n변동률 : " + fluctate_rate_24H + "%" + "\n\n현재시간 : \n" + tt;
                            }
                        }
                        else {
                            while(true) {
                                api = reader.readLine();
                                if (api == null)
                                    break;
                                JSONObject entire_text = new JSONObject(api);
                                JSONArray jsonArray = entire_text.getJSONArray("data");
                                String cur = NumberFormat.getInstance().format(jsonArray.getJSONObject(19).getDouble("price"));
                                current_price = cur;
                            }
                        }
                        reader.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //
    String symboltokor (String symbol){
        String kor = null;
        if(symbol.equals("ETH")) kor = "이더리움";
        else if(symbol.equals("XRP")) kor = "리플";
        else if(symbol.equals("BTC")) kor = "비트코인";
        else if(symbol.equals("ETC")) kor = "이더리움 클래식";
        else if(symbol.equals("ADA")) kor = "에이다";
        else if(symbol.equals("EOS")) kor = "이오스";
        else if(symbol.equals("LF")) kor = "링크플로우";
        else if(symbol.equals("YFI")) kor = "연파이낸스";
        else if(symbol.equals("BCH")) kor = "비트코인 캐시";
        else if(symbol.equals("DOGE")) kor = "도지코인";
        else if(symbol.equals("LUNA")) kor = "루나";
        else if(symbol.equals("ANW")) kor = "앵커뉴럴월드";
        else if(symbol.equals("QTUM")) kor = "퀀텀";
        else if(symbol.equals("BTT")) kor = "비트토렌트";
        else if(symbol.equals("DOT")) kor = "폴카닷";
        else if(symbol.equals("VET")) kor = "비체인";
        else if(symbol.equals("LTC")) kor = "라이트코인";
        else if(symbol.equals("ASM")) kor = "어셈블프로토콜";
        else if(symbol.equals("XLM")) kor = "스텔라루멘";
        else if(symbol.equals("LINK")) kor = "체인링크";
        else if(symbol.equals("MVC")) kor = "마일벌스";
        else if(symbol.equals("TRX")) kor = "트론";
        else if(symbol.equals("ONG")) kor = "온톨로지가스";
        else if(symbol.equals("DVP")) kor = "디브이피";
        else if(symbol.equals("OMG")) kor = "오미세고";
        else if(symbol.equals("MKR")) kor = "메이커";
        else if(symbol.equals("KLAY")) kor = "클레이튼";
        else if(symbol.equals("BSV")) kor = "비트코인에스브이";
        else if(symbol.equals("RLC")) kor = "아이젝";
        else if(symbol.equals("BCD")) kor = "비트코인 다이아몬드";
        else if(symbol.equals("XNO")) kor = "제노토큰";
        else if(symbol.equals("SXP")) kor = "스와이프";
        else if(symbol.equals("PUNDIX")) kor = "펀디엑스";
        else if(symbol.equals("ORC")) kor = "오르빗 체인";
        else if(symbol.equals("MANA")) kor = "디센트럴랜드";
        else if(symbol.equals("ENJ")) kor = "엔진코인";
        else if(symbol.equals("XEM")) kor = "넴";
        else if(symbol.equals("BTG")) kor = "비트코인 골드";
        else if(symbol.equals("BURGER")) kor = "버거스왑";
        else if(symbol.equals("FCT")) kor = "피르마체인";
        else if(symbol.equals("COLA")) kor = "콜라토큰";
        else if(symbol.equals("HIVE")) kor = "하이브";
        else if(symbol.equals("THETA")) kor = "쎄타토큰";
        else if(symbol.equals("ARW")) kor = "아로와나토큰";
        else if(symbol.equals("XTZ")) kor = "테조스";
        else if(symbol.equals("UNI")) kor = "유니스왑";
        else if(symbol.equals("REP")) kor = "어거";
        else if(symbol.equals("STEEM")) kor = "스팀";
        else if(symbol.equals("ONT")) kor = "온톨로지";
        else if(symbol.equals("WAVES")) kor = "웨이브";
        else if(symbol.equals("SUSHI")) kor = "스시스왑";
        else if(symbol.equals("TFUEL")) kor = "쎄타퓨엘";
        else if(symbol.equals("AQT")) kor = "알파쿼크";
        else if(symbol.equals("RNT")) kor = "원루트 네트워크";
        else if(symbol.equals("SAND")) kor = "샌드박스";
        else if(symbol.equals("MLK")) kor = "밀크";
        else if(symbol.equals("LPT")) kor = "라이브피어";
        else if(symbol.equals("ATOM")) kor = "코스모스";
        else if(symbol.equals("SRM")) kor = "세럼";
        else if(symbol.equals("TEMCO")) kor = "템코";
        else if(symbol.equals("DVI")) kor = "디비전";
        else if(symbol.equals("GRT")) kor = "더그래프";
        else if(symbol.equals("AMO")) kor = "아모코인";
        else if(symbol.equals("BORA")) kor = "보라";
        else if(symbol.equals("COMP")) kor = "컴파운드";
        else if(symbol.equals("AOA")) kor = "오로라";
        else if(symbol.equals("QTCON")) kor = "퀴즈톡";
        else if(symbol.equals("MTL")) kor = "메탈";
        else if(symbol.equals("CKB")) kor = "너보스";
        else if(symbol.equals("SNT")) kor = "스테이터스네트워크토큰";
        else if(symbol.equals("BAT")) kor = "베이직어텐션토큰";
        else if(symbol.equals("SSX")) kor = "썸씽";
        else if(symbol.equals("UMA")) kor = "우마";
        else if(symbol.equals("ZRX")) kor = "제로엑스";
        else if(symbol.equals("IOST")) kor = "이오스트";
        else if(symbol.equals("SNX")) kor = "신세틱스";
        else if(symbol.equals("ANKR")) kor = "앵커";
        else if(symbol.equals("GXC")) kor = "지엑스체인";
        else if(symbol.equals("BOA")) kor = "보아";
        else if(symbol.equals("ORBS")) kor = "오브스";
        else if(symbol.equals("CRO")) kor = "크립토닷컴체인";
        else if(symbol.equals("CVT")) kor = "사이버베인";
        else if(symbol.equals("MBL")) kor = "무비블록";
        else if(symbol.equals("ANV")) kor = "애니버스";
        else if(symbol.equals("META")) kor = "메타디움";
        else if(symbol.equals("AAVE")) kor = "에이브";
        else if(symbol.equals("KNC")) kor = "카이버 네트워크";
        else if(symbol.equals("DVC")) kor = "드래곤베인";
        else if(symbol.equals("TMTG")) kor = "더마이다스터치골드";
        else if(symbol.equals("QKC")) kor = "쿼크체인";
        else if(symbol.equals("ALICE")) kor = "마이네이버앨리스";
        else if(symbol.equals("POLA")) kor = "폴라리스 쉐어";
        else if(symbol.equals("MIR")) kor = "미러 프로토콜";
        else if(symbol.equals("LAMB")) kor = "람다";
        else if(symbol.equals("NU")) kor = "누사이퍼";
        else if(symbol.equals("WAXP")) kor = "왁스";
        else if(symbol.equals("ALGO")) kor = "알고랜드";
        else if(symbol.equals("EL")) kor = "엘리시아";
        else if(symbol.equals("BIOT")) kor = "바이오패스포트";
        else if(symbol.equals("DAC")) kor = "다빈치";
        else if(symbol.equals("MCI")) kor = "엠씨아이";
        else if(symbol.equals("CHZ")) kor = "칠리즈";
        else if(symbol.equals("AERGO")) kor = "아르고";
        else if(symbol.equals("OBSR")) kor = "옵저버";
        else if(symbol.equals("BCHA")) kor = "비트코인 캐시 에이비씨";
        else if(symbol.equals("EVZ")) kor = "이브이지";
        else if(symbol.equals("FX")) kor = "펑션엑스";
        else if(symbol.equals("FLETA")) kor = "플레타";
        else if(symbol.equals("LINA")) kor = "리니어파이낸스";
        else if(symbol.equals("ZIL")) kor = "질리카";
        else if(symbol.equals("TRUE")) kor = "트루체인";
        else if(symbol.equals("ICX")) kor = "아이콘";
        else if(symbol.equals("OXT")) kor = "오키드";
        else if(symbol.equals("STRAX")) kor = "스트라티스";
        else if(symbol.equals("WEMIX")) kor = "위믹스";
        else if(symbol.equals("WICC")) kor = "웨이키체인";
        else if(symbol.equals("CHR")) kor = "크로미아";
        else if(symbol.equals("HDAC")) kor = "에이치닥";
        else if(symbol.equals("FIT")) kor = "300피트 네트워크";
        else if(symbol.equals("LOOM")) kor = "룸네트워크";
        else if(symbol.equals("MAP")) kor = "맵프로토콜";
        else if(symbol.equals("SUN")) kor = "썬";
        else if(symbol.equals("CTXC")) kor = "코르텍스";
        else if(symbol.equals("POWR")) kor = "파워렛저";
        else if(symbol.equals("XPR")) kor = "프로톤";
        else if(symbol.equals("ELF")) kor = "엘프";
        else if(symbol.equals("JST")) kor = "저스트";
        else if(symbol.equals("WTC")) kor = "월튼체인";
        else if(symbol.equals("EM")) kor = "이마이너";
        else if(symbol.equals("BEL")) kor = "벨라프로토콜";
        else if(symbol.equals("LRC")) kor = "루프링";
        else if(symbol.equals("OCEAN")) kor = "오션프로토콜";
        else if(symbol.equals("BASIC")) kor = "베이직";
        else if(symbol.equals("AION")) kor = "아이온";
        else if(symbol.equals("COS")) kor = "콘텐토스";
        else if(symbol.equals("ADP")) kor = "어댑터 토큰";
        else if(symbol.equals("QBZ")) kor = "퀸비";
        else if(symbol.equals("EGG")) kor = "네스트리";
        else if(symbol.equals("RAI")) kor = "라이파이낸스";
        else if(symbol.equals("GLM")) kor = "골렘";
        else if(symbol.equals("AE")) kor = "애터니티";
        else if(symbol.equals("MIX")) kor = "믹스마블";
        else if(symbol.equals("CON")) kor = "코넌";
        else if(symbol.equals("WOZX")) kor = "이포스";
        else if(symbol.equals("ARPA")) kor = "알파체인";
        else if(symbol.equals("FNB")) kor = "애프앤비프로토콜";
        else if(symbol.equals("RLY")) kor = "랠리";
        else if(symbol.equals("RINGX")) kor = "링엑스";
        else if(symbol.equals("SOC")) kor = "소다코인";
        else if(symbol.equals("MM")) kor = "밀리미터토큰";
        else if(symbol.equals("VSYS")) kor = "브이시스템즈";
        else if(symbol.equals("GOM2")) kor = "고머니2";
        else if(symbol.equals("APIX")) kor = "아픽스";
        else if(symbol.equals("MXC")) kor = "머신익스체인지코인";
        else if(symbol.equals("WOM")) kor = "왐토큰";
        else if(symbol.equals("TRV")) kor = "트러스트버스";
        else if(symbol.equals("PCM")) kor = "프레시움";
        else if(symbol.equals("AWO")) kor = "에이아이워크";
        else if(symbol.equals("IPX")) kor = "타키온프로토콜";
        else if(symbol.equals("DAD")) kor = "다드";
        else if(symbol.equals("CENNZ")) kor = "센트럴리티";
        else if(symbol.equals("APM")) kor = "에이피엠 코인";
        else if(symbol.equals("VALOR")) kor = "밸러토큰";
        return kor;
    }
    String kortosymbol (String kor){
        String symbol = null;
        if(kor.equals("이더리움")) symbol = "ETH";
        else if(kor.equals("리플")) symbol = "XRP";
        else if(kor.equals("비트코인")) symbol = "BTC";
        else if(kor.equals("이더리움 클래식")) symbol = "ETC";
        else if(kor.equals("에이다")) symbol = "ADA";
        else if(kor.equals("이오스")) symbol = "EOS";
        else if(kor.equals("링크플로우")) symbol = "LF";
        else if(kor.equals("연파이낸스")) symbol = "YFI";
        else if(kor.equals("비트코인 캐시")) symbol = "BCH";
        else if(kor.equals("도지코인")) symbol = "DOGE";
        else if(kor.equals("루나")) symbol = "LUNA";
        else if(kor.equals("앵커뉴럴월드")) symbol = "ANW";
        else if(kor.equals("퀀텀")) symbol = "QTUM";
        else if(kor.equals("비트토렌트")) symbol = "BTT";
        else if(kor.equals("폴카닷")) symbol = "DOT";
        else if(kor.equals("비체인")) symbol = "VET";
        else if(kor.equals("라이트코인")) symbol = "LTC";
        else if(kor.equals("어셈블프로토콜")) symbol = "ASM";
        else if(kor.equals("스텔라루멘")) symbol = "XLM";
        else if(kor.equals("체인링크")) symbol = "LINK";
        else if(kor.equals("마일벌스")) symbol = "MVC";
        else if(kor.equals("트론")) symbol = "TRX";
        else if(kor.equals("온톨로지가스")) symbol = "ONG";
        else if(kor.equals("디브이피")) symbol = "DVP";
        else if(kor.equals("오미세고")) symbol = "OMG";
        else if(kor.equals("메이커")) symbol = "MKR";
        else if(kor.equals("클레이튼")) symbol = "KLAY";
        else if(kor.equals("비트코인에스브이")) symbol = "BSV";
        else if(kor.equals("아이젝")) symbol = "RLC";
        else if(kor.equals("비트코인 다이아몬드")) symbol = "BCD";
        else if(kor.equals("제노토큰")) symbol = "XNO";
        else if(kor.equals("스와이프")) symbol = "SXP";
        else if(kor.equals("펀디엑스")) symbol = "PUNDIX";
        else if(kor.equals("오르빗 체인")) symbol = "ORC";
        else if(kor.equals("디센트럴랜드")) symbol = "MANA";
        else if(kor.equals("엔진코인")) symbol = "ENJ";
        else if(kor.equals("넴")) symbol = "XEM";
        else if(kor.equals("비트코인 골드")) symbol = "BTG";
        else if(kor.equals("버거스왑")) symbol = "BURGER";
        else if(kor.equals("피르마체인")) symbol = "FCT";
        else if(kor.equals("콜라토큰")) symbol = "COLA";
        else if(kor.equals("하이브")) symbol = "HIVE";
        else if(kor.equals("쎄타토큰")) symbol = "THETA";
        else if(kor.equals("아로와나토큰")) symbol = "ARW";
        else if(kor.equals("테조스")) symbol = "XTZ";
        else if(kor.equals("유니스왑")) symbol = "UNI";
        else if(kor.equals("어거")) symbol = "REP";
        else if(kor.equals("스팀")) symbol = "STEEM";
        else if(kor.equals("온톨로지")) symbol = "ONT";
        else if(kor.equals("웨이브")) symbol = "WAVES";
        else if(kor.equals("스시스왑")) symbol = "SUSHI";
        else if(kor.equals("쎄타퓨엘")) symbol = "TFUEL";
        else if(kor.equals("알파쿼크")) symbol = "AQT";
        else if(kor.equals("원루트 네트워크")) symbol = "RNT";
        else if(kor.equals("샌드박스")) symbol = "SAND";
        else if(kor.equals("밀크")) symbol = "MLK";
        else if(kor.equals("라이브피어")) symbol = "LPT";
        else if(kor.equals("코스모스")) symbol = "ATOM";
        else if(kor.equals("세럼")) symbol = "SRM";
        else if(kor.equals("템코")) symbol = "TEMCO";
        else if(kor.equals("디비전")) symbol = "DVI";
        else if(kor.equals("더그래프")) symbol = "GRT";
        else if(kor.equals("아모코인")) symbol = "AMO";
        else if(kor.equals("보라")) symbol = "BORA";
        else if(kor.equals("컴파운드")) symbol = "COMP";
        else if(kor.equals("오로라")) symbol = "AOA";
        else if(kor.equals("퀴즈톡")) symbol = "QTCON";
        else if(kor.equals("메탈")) symbol = "MTL";
        else if(kor.equals("너보스")) symbol = "CKB";
        else if(kor.equals("스테이터스네트워크토큰")) symbol = "SNT";
        else if(kor.equals("베이직어텐션토큰")) symbol = "BAT";
        else if(kor.equals("썸씽")) symbol = "SSX";
        else if(kor.equals("우마")) symbol = "UMA";
        else if(kor.equals("제로엑스")) symbol = "ZRX";
        else if(kor.equals("이오스트")) symbol = "IOST";
        else if(kor.equals("신세틱스")) symbol = "SNX";
        else if(kor.equals("앵커")) symbol = "ANKR";
        else if(kor.equals("지엑스체인")) symbol = "GXC";
        else if(kor.equals("보아")) symbol = "BOA";
        else if(kor.equals("오브스")) symbol = "ORBS";
        else if(kor.equals("크립토닷컴체인")) symbol = "CRO";
        else if(kor.equals("사이버베인")) symbol = "CVT";
        else if(kor.equals("무비블록")) symbol = "MBL";
        else if(kor.equals("애니버스")) symbol = "ANV";
        else if(kor.equals("메타디움")) symbol = "META";
        else if(kor.equals("에이브")) symbol = "AAVE";
        else if(kor.equals("카이버 네트워크")) symbol = "KNC";
        else if(kor.equals("드래곤베인")) symbol = "DVC";
        else if(kor.equals("더마이다스터치골드")) symbol = "TMTG";
        else if(kor.equals("쿼크체인")) symbol = "QKC";
        else if(kor.equals("마이네이버앨리스")) symbol = "ALICE";
        else if(kor.equals("폴라리스 쉐어")) symbol = "POLA";
        else if(kor.equals("미러 프로토콜")) symbol = "MIR";
        else if(kor.equals("람다")) symbol = "LAMB";
        else if(kor.equals("누사이퍼")) symbol = "NU";
        else if(kor.equals("왁스")) symbol = "WAXP";
        else if(kor.equals("알고랜드")) symbol = "ALGO";
        else if(kor.equals("엘리시아")) symbol = "EL";
        else if(kor.equals("바이오패스포트")) symbol = "BIOT";
        else if(kor.equals("다빈치")) symbol = "DAC";
        else if(kor.equals("엠씨아이")) symbol = "MCI";
        else if(kor.equals("칠리즈")) symbol = "CHZ";
        else if(kor.equals("아르고")) symbol = "AERGO";
        else if(kor.equals("옵저버")) symbol = "OBSR";
        else if(kor.equals("비트코인 캐시 에이비씨")) symbol = "BCHA";
        else if(kor.equals("이브이지")) symbol = "EVZ";
        else if(kor.equals("펑션엑스")) symbol = "FX";
        else if(kor.equals("플레타")) symbol = "FLETA";
        else if(kor.equals("리니어파이낸스")) symbol = "LINA";
        else if(kor.equals("질리카")) symbol = "ZIL";
        else if(kor.equals("트루체인")) symbol = "TRUE";
        else if(kor.equals("아이콘")) symbol = "ICX";
        else if(kor.equals("오키드")) symbol = "OXT";
        else if(kor.equals("스트라티스")) symbol = "STRAX";
        else if(kor.equals("위믹스")) symbol = "WEMIX";
        else if(kor.equals("웨이키체인")) symbol = "WICC";
        else if(kor.equals("크로미아")) symbol = "CHR";
        else if(kor.equals("에이치닥")) symbol = "HDAC";
        else if(kor.equals("300피트 네트워크")) symbol = "FIT";
        else if(kor.equals("룸네트워크")) symbol = "LOOM";
        else if(kor.equals("맵프로토콜")) symbol = "MAP";
        else if(kor.equals("썬")) symbol = "SUN";
        else if(kor.equals("코르텍스")) symbol = "CTXC";
        else if(kor.equals("파워렛저")) symbol = "POWR";
        else if(kor.equals("프로톤")) symbol = "XPR";
        else if(kor.equals("엘프")) symbol = "ELF";
        else if(kor.equals("저스트")) symbol = "JST";
        else if(kor.equals("월튼체인")) symbol = "WTC";
        else if(kor.equals("이마이너")) symbol = "EM";
        else if(kor.equals("벨라프로토콜")) symbol = "BEL";
        else if(kor.equals("루프링")) symbol = "LRC";
        else if(kor.equals("오션프로토콜")) symbol = "OCEAN";
        else if(kor.equals("베이직")) symbol = "BASIC";
        else if(kor.equals("아이온")) symbol = "AION";
        else if(kor.equals("콘텐토스")) symbol = "COS";
        else if(kor.equals("어댑터 토큰")) symbol = "ADP";
        else if(kor.equals("퀸비")) symbol = "QBZ";
        else if(kor.equals("네스트리")) symbol = "EGG";
        else if(kor.equals("라이파이낸스")) symbol = "RAI";
        else if(kor.equals("골렘")) symbol = "GLM";
        else if(kor.equals("애터니티")) symbol = "AE";
        else if(kor.equals("믹스마블")) symbol = "MIX";
        else if(kor.equals("코넌")) symbol = "CON";
        else if(kor.equals("이포스")) symbol = "WOZX";
        else if(kor.equals("알파체인")) symbol = "ARPA";
        else if(kor.equals("애프앤비프로토콜")) symbol = "FNB";
        else if(kor.equals("랠리")) symbol = "RLY";
        else if(kor.equals("링엑스")) symbol = "RINGX";
        else if(kor.equals("소다코인")) symbol = "SOC";
        else if(kor.equals("밀리미터토큰")) symbol = "MM";
        else if(kor.equals("브이시스템즈")) symbol = "VSYS";
        else if(kor.equals("고머니2")) symbol = "GOM2";
        else if(kor.equals("아픽스")) symbol = "APIX";
        else if(kor.equals("머신익스체인지코인")) symbol = "MXC";
        else if(kor.equals("왐토큰")) symbol = "WOM";
        else if(kor.equals("트러스트버스")) symbol = "TRV";
        else if(kor.equals("프레시움")) symbol = "PCM";
        else if(kor.equals("에이아이워크")) symbol = "AWO";
        else if(kor.equals("타키온프로토콜")) symbol = "IPX";
        else if(kor.equals("다드")) symbol = "DAD";
        else if(kor.equals("센트럴리티")) symbol = "CENNZ";
        else if(kor.equals("에이피엠 코인")) symbol = "APM";
        else if(kor.equals("밸러토큰")) symbol = "VALOR";
        return symbol;
    }
    String[] kor_coin_arr = {"이더리움",
            "리플",
            "비트코인",
            "이더리움 클래식",
            "에이다",
            "이오스",
            "링크플로우",
            "연파이낸스",
            "비트코인 캐시",
            "도지코인",
            "루나",
            "앵커뉴럴월드",
            "퀀텀",
            "비트토렌트",
            "폴카닷",
            "비체인",
            "라이트코인",
            "어셈블프로토콜",
            "스텔라루멘",
            "체인링크",
            "마일벌스",
            "트론",
            "온톨로지가스",
            "디브이피",
            "오미세고",
            "메이커",
            "클레이튼",
            "비트코인에스브이",
            "아이젝",
            "비트코인 다이아몬드",
            "제노토큰",
            "스와이프",
            "펀디엑스",
            "오르빗 체인",
            "디센트럴랜드",
            "엔진코인",
            "넴",
            "비트코인 골드",
            "버거스왑",
            "피르마체인",
            "콜라토큰",
            "하이브",
            "쎄타토큰",
            "아로와나토큰",
            "테조스",
            "유니스왑",
            "어거",
            "스팀",
            "온톨로지",
            "웨이브",
            "스시스왑",
            "쎄타퓨엘",
            "알파쿼크",
            "원루트 네트워크",
            "샌드박스",
            "밀크",
            "라이브피어",
            "코스모스",
            "세럼",
            "템코",
            "디비전",
            "더그래프",
            "아모코인",
            "보라",
            "컴파운드",
            "오로라",
            "퀴즈톡",
            "메탈",
            "너보스",
            "스테이터스네트워크토큰",
            "베이직어텐션토큰",
            "썸씽",
            "우마",
            "제로엑스",
            "이오스트",
            "신세틱스",
            "앵커",
            "지엑스체인",
            "보아",
            "오브스",
            "크립토닷컴체인",
            "사이버베인",
            "무비블록",
            "애니버스",
            "메타디움",
            "에이브",
            "카이버 네트워크",
            "드래곤베인",
            "더마이다스터치골드",
            "쿼크체인",
            "마이네이버앨리스",
            "폴라리스 쉐어",
            "미러 프로토콜",
            "람다",
            "누사이퍼",
            "왁스",
            "알고랜드",
            "엘리시아",
            "바이오패스포트",
            "다빈치",
            "엠씨아이",
            "칠리즈",
            "아르고",
            "옵저버",
            "비트코인 캐시 에이비씨",
            "이브이지",
            "펑션엑스",
            "플레타",
            "리니어파이낸스",
            "질리카",
            "트루체인",
            "아이콘",
            "오키드",
            "스트라티스",
            "위믹스",
            "웨이키체인",
            "크로미아",
            "에이치닥",
            "300피트 네트워크",
            "룸네트워크",
            "맵프로토콜",
            "썬",
            "코르텍스",
            "파워렛저",
            "프로톤",
            "엘프",
            "저스트",
            "월튼체인",
            "이마이너",
            "벨라프로토콜",
            "루프링",
            "오션프로토콜",
            "베이직",
            "아이온",
            "콘텐토스",
            "어댑터 토큰",
            "퀸비",
            "네스트리",
            "라이파이낸스",
            "골렘",
            "애터니티",
            "믹스마블",
            "코넌",
            "이포스",
            "알파체인",
            "애프앤비프로토콜",
            "랠리",
            "링엑스",
            "소다코인",
            "밀리미터토큰",
            "브이시스템즈",
            "고머니2",
            "아픽스",
            "머신익스체인지코인",
            "왐토큰",
            "트러스트버스",
            "프레시움",
            "에이아이워크",
            "타키온프로토콜",
            "다드",
            "센트럴리티",
            "에이피엠 코인",
            "밸러토큰"};
}
