package com.umeng.yangzhong2019120.utils;

import android.util.Log;

import com.umeng.yangzhong2019120.inter.ICallBack;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitUtils {

    private static volatile RetrofitUtils instance;
    private OkHttpClient client;

    public RetrofitUtils() {
        //设置日志拦截器等级
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //创建日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印日志信息
                Log.i("日志信息", "log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(level);

        //创建okhttp
        client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //将client对象加入retrofit


    }

    //单例双重锁
    public static  RetrofitUtils getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtils.class) {
                if (instance == null) {
                    return instance = new RetrofitUtils();
                }
            }
        }
        return instance;
    }

    public void doShowshop(String url, Map<String, String> map, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .build();

        MyInterFace myInterFace = retrofit.create(MyInterFace.class);

        Call<ResponseBody> showshop = myInterFace.getShowshop(map);
        showshop.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callBack.onSuccess(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailed();
            }
        });

    }
    public void doXiangqing(String url, Map<String, String> map, final ICallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .build();

        MyInterFace myInterFace = retrofit.create(MyInterFace.class);

        Call<ResponseBody> xiangqing = myInterFace.Xiangqing(map);
        xiangqing.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callBack.onSuccess(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailed();
            }
        });

    }

}
