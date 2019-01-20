package com.umeng.yangzhong2019120;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abner.ming.MingUtils;
import com.abner.ming.ResultListener;
import com.abner.ming.UmengBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.umeng.yangzhong2019120.bean.XiangqingBean;
import com.umeng.yangzhong2019120.presenter.ShowPresenter;
import com.umeng.yangzhong2019120.presenter.ShowPresenterInterFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.abner.ming.MingUtils.*;

public class XiangqingActivity extends AppCompatActivity {

    @BindView(R.id.sdv_head)
    ImageView sdvHead;
    @BindView(R.id.tv_fenxiang)
    TextView tvFenxiang;
    @BindView(R.id.vp_banner)
    ViewPager vpBanner;
    @BindView(R.id.tv_xiangqingname)
    TextView tvXiangqingname;
    @BindView(R.id.tv_xiangqingprice)
    TextView tvXiangqingprice;
    @BindView(R.id.tv_addshopcar)
    TextView tvAddshopcar;
    private List<XiangqingBean.DataBean> xiangqinglist=new ArrayList<>();
    private ShowPresenter showPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiangqing);
        ButterKnife.bind(this);
        //接收传过来的值
        Intent intent=getIntent();
        intent.getStringExtra("pid");
        //初始化p层
        initPresenter();
    }

    private void initPresenter() {
        showPresenter = new ShowPresenter();
        HashMap<String, String> map = new HashMap<>();
        map.put("pid","pid");
        showPresenter.Xiangqing(map, new ShowPresenterInterFace() {
            @Override
            public void onSuccess(String json) {
                if (json.equals("{}")){
                    Toast.makeText(XiangqingActivity.this, "没有此商品", Toast.LENGTH_SHORT).show();
                }
                Gson gson=new Gson();
                final XiangqingBean xiangqingBean = gson.fromJson(json, XiangqingBean.class);
                final XiangqingBean.DataBean data = xiangqingBean.getData();
                tvXiangqingname.setText(data.getTitle());
                tvXiangqingprice.setText(data.getPrice()+"");
                vpBanner.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return xiangqinglist.size();
                    }

                    @Override
                    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                        return view==o;
                    }

                    @NonNull
                    @Override
                    public Object instantiateItem(@NonNull ViewGroup container, int position) {
                        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(XiangqingActivity.this);
                        simpleDraweeView.setScaleType(SimpleDraweeView.ScaleType.FIT_XY);
                        String images = data.getImages();
                        String[] split = images.split("\\|");
                        simpleDraweeView.setImageURI(split[0]);
                        container.addView(simpleDraweeView);
                        return simpleDraweeView;
                    }

                    @Override
                    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                        container.removeView((View) object);
                    }
                });

            }

            @Override
            public void onfailed() {

            }
        });
    }

    @OnClick({R.id.sdv_head, R.id.tv_fenxiang, R.id.tv_addshopcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sdv_head:
                login(XiangqingActivity.this, 0, new ResultListener() {
                    @Override
                    public void success(UmengBean umengBean) {

                    }
                });
                break;
            case R.id.tv_fenxiang:
                shared(XiangqingActivity.this,0,"","","","");
                break;
            case R.id.tv_addshopcar:
                Toast.makeText(this, "此商品已加入购物车", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (showPresenter!=null){
            showPresenter=null;
        }
    }
}
