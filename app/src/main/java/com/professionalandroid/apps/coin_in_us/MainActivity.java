package com.professionalandroid.apps.coin_in_us;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.professionalandroid.apps.coin_in_us.ui.coininfo.Coin_Info_Fragment;
import com.professionalandroid.apps.coin_in_us.ui.stockinfo.StockInfo;

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
    //
    public void coinfo(String name) {
        //
        String kor_name = symboltokor(name);
        urlStr = "https://api.bithumb.com/public/ticker/"+name+"_KRW";
        RequestThread order_thread = new RequestThread();
        order_thread.start();
        //
        handler.postDelayed(new Runnable()  {
            public void run() {
                urlStr = "https://api.bithumb.com/public/transaction_history/"+name+"_KRW";
                RequestThread ticker_thread = new RequestThread();
                ticker_thread.start();
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

    public void stock() {
        Intent intent = new Intent(this, StockInfo.class);
        startActivity(intent);
    }
    //
    public String symboltokor (String symbol){
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
}

//coin _ search branch....
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        SearchView searchView;
//        searchView = findViewById(R.id.search_view);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                 R.id.navigation_coin, R.id.navigation_home, R.id.navigation_stock)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_view);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
//    }
//}
