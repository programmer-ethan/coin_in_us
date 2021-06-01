package com.professionalandroid.apps.coin_in_us.model;

public class CoinModel {
        public String coin_nm;
        public String opening_price;
        public String fluctate_rate_24H;

        public String getCoin_nm() { return coin_nm; }
        public String getopeningprice() { return opening_price; }
        public String getfluctaterate24h() { return fluctate_rate_24H; }


    public CoinModel() {
    }

    public CoinModel(String opening_price, String fluctate_rate_24H) {
        this.opening_price = opening_price;
        this.fluctate_rate_24H = fluctate_rate_24H;
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
