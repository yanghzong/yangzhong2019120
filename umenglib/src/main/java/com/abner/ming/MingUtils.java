package com.abner.ming;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * author:AbnerMing
 * date:2018/10/19
 * 登录分享工具类
 */
public class MingUtils {
    private static String TAG = MingUtils.class.getSimpleName();

    /**
     * 登录调用方法
     * type类型
     * 0:QQ登录
     * 1：微信登录
     * 2：新浪登录
     * */
    public static void login(AppCompatActivity context, int type, final ResultListener listener){
        SHARE_MEDIA state = SHARE_MEDIA.QQ;
        if(type==0){
            state = SHARE_MEDIA.QQ;
        }else if(type==1){
            state = SHARE_MEDIA.WEIXIN;
        }else if(type==2){
            state = SHARE_MEDIA.SINA;
        }
        UMShareAPI.get(context).getPlatformInfo(context, state, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                UmengBean umengBean=new UmengBean();
                umengBean.setName(name);
                umengBean.setGender(gender);
                umengBean.setIconurl(iconurl);

                listener.success(umengBean);

                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }



    /***
     * 分享
     *      context 为上下文，可传this
     *      type 为类型 0 ：QQ分享，1：微信分享，2：新浪微博分享，3：QQ分享，4：微信朋友圈分享
     *      title 为分享标题
     *      desc 为分享描述
     *      image 为分享图片链接
     *      link  为跳转链接
     *
     *      参数可传空，为空则取默认值
     *
     * */
    public static void shared(AppCompatActivity context,int type,String title,
                              String desc,String image,String link){
        if(TextUtils.isEmpty(title)){
            title="北京八维";
        }
        if(TextUtils.isEmpty(desc)){
            desc="北京八维";
        }
        if(TextUtils.isEmpty(image)){
            image="http://dev.umeng.com/images/tab2_1.png";
        }
        if(TextUtils.isEmpty(link)){
            link="http://www.vipandroid.cn/";
        }
        SHARE_MEDIA state = SHARE_MEDIA.QQ;
        if(type==0){
            state = SHARE_MEDIA.QQ;//QQ
        }else if(type==1){
            state = SHARE_MEDIA.WEIXIN;//微信
        }else if(type==2){
            state = SHARE_MEDIA.SINA;//新浪
        }else if(type==3){
            state = SHARE_MEDIA.QZONE;//qq空间
        }else if(type==4){
            state = SHARE_MEDIA.WEIXIN_CIRCLE;//微信朋友圈
        }
        ShareUtils.shareWeb(context, link, title
                , desc, image, R.mipmap.icon_logo_share, state
        );

    }


}
