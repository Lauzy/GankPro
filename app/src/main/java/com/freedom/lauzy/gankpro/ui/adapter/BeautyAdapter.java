package com.freedom.lauzy.gankpro.ui.adapter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.ui.activity.DailyActivity;

import java.util.List;

/**
 * BaseQuickAdapter方便添加header，footer，emptyView，以及加载更多
 * 设置多类型item时需数据设置itemType
 * Created by Lauzy on 2017/1/22.
 */

public class BeautyAdapter extends BaseQuickAdapter<GankData.ResultsBean, BaseViewHolder> {


   /* public BeautyAdapter(List<GankData.ResultsBean> data) {
        super(data);
        addItemType(GankData.ResultsBean.TYPE_ONE,R.layout.layout_beauty_item);
        addItemType(GankData.ResultsBean.TYPE_TWO,R.layout.layout_beauty_item_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankData.ResultsBean item) {
        *//*if (helper.getLayoutPosition() % 3 == 0){
            item.setItemType(GankData.ResultsBean.TYPE_ONE);
        }else {
            item.setItemType(GankData.ResultsBean.TYPE_TWO);
        }*//*

        switch (helper.getItemViewType()) {
            case GankData.ResultsBean.TYPE_ONE:
                Glide.with(mContext).load(item.getUrl()).centerCrop().placeholder(R.mipmap.ic_launcher)
                        .into((ImageView) helper.getView(R.id.img_beauty_item_one));
                break;
            case GankData.ResultsBean.TYPE_TWO:
                Glide.with(mContext).load(item.getUrl()).centerCrop().placeholder(R.mipmap.ic_launcher)
                        .into((ImageView) helper.getView(R.id.img_beauty_item_two));
                break;
        }
    }*/


    public BeautyAdapter(int layoutResId, List<GankData.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankData.ResultsBean item) {
        Glide.with(mContext).load(item.getUrl()).placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .into((ImageView) helper.getView(R.id.img_beauty_item));
        helper.getView(R.id.img_beauty_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DailyActivity.class);
                if (Build.VERSION.SDK_INT >= 21) {
                    mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mContext, v, "transitionImg").toBundle());
                }
            }
        });
    }
}
