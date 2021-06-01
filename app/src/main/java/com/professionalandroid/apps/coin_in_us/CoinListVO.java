package com.professionalandroid.apps.coin_in_us;

import androidx.collection.SparseArrayCompat;

import java.io.StringReader;
import java.util.ArrayList;

public class CoinListVO extends CoinList {
    private String category;
    private ArrayList<CoinList>list;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<CoinList> getList() {
        return list;
    }

    public void setList(ArrayList<CoinList>list) {
        this.list = list;
    }

    public class CoinList{
        private String coin_nm;
        private String opening_price;
        private String fluctate_rate_24H;

        public String getCoin_nm() {
            return coin_nm;
        }

        public void setCoin_nm(String coin_nm) {
            this.coin_nm = coin_nm;
        }

        public String getOpening_price() {
            return opening_price;
        }

        public void setOpening_price(String opening_price) {
            this.opening_price = opening_price;
        }

        public String getFluctate_rate_24H() {
            return fluctate_rate_24H;
        }

        public void setFluctate_rate_24H(String fluctate_rate_24H) {
            this.fluctate_rate_24H = fluctate_rate_24H;
        }
    }
}
