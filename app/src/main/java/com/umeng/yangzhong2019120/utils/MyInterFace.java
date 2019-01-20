package com.umeng.yangzhong2019120.utils;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyInterFace {

    @GET("getProducts")
    Call<ResponseBody> getShowshop(@QueryMap Map<String,String>map);
    @GET("getProductDetail")
    Call<ResponseBody> Xiangqing(@QueryMap Map<String,String>map);

}
