package com.umeng.yangzhong2019120.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.yangzhong2019120.R;
import com.umeng.yangzhong2019120.bean.ShowShopBean;

import java.util.List;

public class ShowShopAdapter  extends XRecyclerView.Adapter<ShowShopAdapter.ViewHolder> {


    private Context context;
    private List<ShowShopBean.DataBean> list;

    public ShowShopAdapter(Context context, List<ShowShopBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    public void setList(List<ShowShopBean.DataBean> beans,int page){
        if (page==1){
            this.list.clear();
        }
        if (beans!=null){
            this.list.addAll(beans);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  itemView=View.inflate(context, R.layout.show_item,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ShowShopBean.DataBean dataBean = list.get(i);
        String images = dataBean.getImages();
        String[] split = images.split("\\|");
        viewHolder.showImg.setImageURI(split[0]);
        viewHolder.tvShowName.setText(dataBean.getTitle());
        viewHolder.tvShowPrice.setText("$:"+dataBean.getPrice());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetClickListener.onClick(dataBean.getPid());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onSetlongClickListener.onClick();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView showImg;
        private final TextView tvShowName;
        private final TextView tvShowPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showImg = itemView.findViewById(R.id.show_img);
            tvShowName = itemView.findViewById(R.id.tv_showname);
            tvShowPrice = itemView.findViewById(R.id.tv_showprice);
        }
    }
    public interface  OnSetlongClickListener{
        void  onClick();
    }

    OnSetlongClickListener onSetlongClickListener;

    public  void setOnSetlongClickListener(OnSetlongClickListener onSetlongClickListener){
        this.onSetlongClickListener=onSetlongClickListener;
    }


    public interface  OnSetClickListener{
        void  onClick(int pid);
    }

    OnSetClickListener onSetClickListener;

    public  void setOnSetClickListener(OnSetClickListener onSetClickListener){
        this.onSetClickListener=onSetClickListener;
    }

    public  void  removeDate(int i){
        list.remove(i);
        notifyItemRemoved(i);
    }
}
