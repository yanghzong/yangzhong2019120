package com.umeng.yangzhong2019120.presenter;

import com.umeng.yangzhong2019120.inter.Apis;
import com.umeng.yangzhong2019120.inter.ICallBack;
import com.umeng.yangzhong2019120.utils.RetrofitUtils;

import java.util.Map;

public class ShowPresenter {
    public  void  Showshop(Map<String,String> map, final ShowPresenterInterFace showPresenterInterFace){
        new RetrofitUtils().getInstance().doShowshop(Apis.ShowShopUrl, map, new ICallBack() {
            @Override
            public void onSuccess(String json) {
                showPresenterInterFace.onSuccess(json);
            }

            @Override
            public void onFailed() {
                showPresenterInterFace.onfailed();
            }
        });
    }
    public  void  Xiangqing(Map<String,String> map, final ShowPresenterInterFace showPresenterInterFace){
        new RetrofitUtils().getInstance().doXiangqing(Apis.Xiangqing, map, new ICallBack() {
            @Override
            public void onSuccess(String json) {
                showPresenterInterFace.onSuccess(json);
            }

            @Override
            public void onFailed() {
                showPresenterInterFace.onfailed();
            }
        });
    }
}
