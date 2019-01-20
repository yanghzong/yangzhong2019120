package com.umeng.yangzhong2019120;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.yangzhong2019120.adapter.ShowShopAdapter;
import com.umeng.yangzhong2019120.bean.ShowShopBean;
import com.umeng.yangzhong2019120.presenter.ShowPresenter;
import com.umeng.yangzhong2019120.presenter.ShowPresenterInterFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.map_layout)
    TextView mapLayout;
    @BindView(R.id.tv_zonghe)
    TextView tvZonghe;
    @BindView(R.id.tv_xiaoliang)
    TextView tvXiaoliang;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.xrv_show)
    XRecyclerView xrvShow;
    private int page=1;
    private ShowPresenter showPresenter;
    private List<ShowShopBean.DataBean> showlist;
    private ShowShopAdapter showShopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化p层
        initPresenter();
        //初始化adapter
        initAdapter();
        xrvShow.setPullRefreshEnabled(true);
        xrvShow.setLoadingMoreEnabled(true);
        xrvShow.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initPresenter();
            }

            @Override
            public void onLoadMore() {
               page++;
               initPresenter();
            }
        });
    }

    private void initAdapter() {
        showlist = new ArrayList<>();
        showShopAdapter = new ShowShopAdapter(this,showlist);
        xrvShow.setAdapter(showShopAdapter);
        xrvShow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        showShopAdapter.setOnSetClickListener(new ShowShopAdapter.OnSetClickListener() {
            @Override
            public void onClick(int pid) {
                Intent intent=new Intent(MainActivity.this,XiangqingActivity.class);
                intent.putExtra("pid",pid+"");
                startActivity(intent);
            }
        });
        showShopAdapter.setOnSetlongClickListener(new ShowShopAdapter.OnSetlongClickListener() {
            @Override
            public void onClick() {
                showShopAdapter.removeDate(0);
            }
        });

    }

    private void initPresenter() {
        showPresenter = new ShowPresenter();
        HashMap<String, String> map = new HashMap<>();
        map.put("pscid","1");
        map.put("page",""+page);

        showPresenter.Showshop(map, new ShowPresenterInterFace() {
            @Override
            public void onSuccess(String json) {
                Gson gson=new Gson();
               /* ShowShopBean showShopBean = gson.fromJson(json, ShowShopBean.class);
                showShopAdapter.setList(showShopBean.getData(),page);
                xrvShow.refreshComplete();*/

            }

            @Override
            public void onfailed() {
                Toast.makeText(MainActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick({R.id.map_layout, R.id.tv_zonghe, R.id.tv_xiaoliang, R.id.tv_price, R.id.tv_choose, R.id.xrv_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_layout:
                Intent intent=new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_zonghe:
                break;
            case R.id.tv_xiaoliang:
                break;
            case R.id.tv_price:
                break;
            case R.id.tv_choose:
                break;
            case R.id.xrv_show:
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
