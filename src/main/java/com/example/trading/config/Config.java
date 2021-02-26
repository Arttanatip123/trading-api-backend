package com.example.trading.config;

public class Config {
//    public static final String IMG_SHOP_URL = "C:/img/shop/";
//    public static final String IMG_PRODUCT_URL = "C:/img/product/";

    public static final String IMG_SHOP_URL = "home/tanatip99950/shop/";
    public static final String IMG_PRODUCT_URL = "home/tanatip99950/product/";


    public static final int DASHBOARD_MOREDATA_SIZE = 10;

    public static final double STORE_DISTANCE = 10.0;

    public static final String MEDIATYPE_UTF8_JSON = "application/json;charset=UTF-8";

    public static final String[] ALLOW_API_PATH = new String[]{
            "/token_check",
            "/user/register",
            "/notificationscreen/list"
    };

    public static final String[] ALLOW_URL = new String[]{
            "http://localhost:3000"};
}
