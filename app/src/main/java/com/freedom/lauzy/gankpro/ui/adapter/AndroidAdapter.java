package com.freedom.lauzy.gankpro.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.NormalAdapter;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.ViewHolder;
import com.freedom.lauzy.gankpro.function.entity.GankData;

import java.util.List;

/**
 * Android
 * Created by Lauzy on 2017/2/4.
 */

public class AndroidAdapter extends NormalAdapter<GankData.ResultsBean> {

    public AndroidAdapter(Context context, List<GankData.ResultsBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void convert(ViewHolder holder, GankData.ResultsBean resultsBean) {
        holder.setText(R.id.txt_android_content, resultsBean.getDesc());
        ImageView imageView = holder.getView(R.id.img_android);
        imageView.setVisibility(resultsBean.getImages() == null ? View.GONE : View.VISIBLE);
        if (resultsBean.getImages() != null) {
            ImageLoader.loadImage(mContext, resultsBean.getImages().get(0) + "?imageView2/0/w/100", imageView);
        }
    }

    public void addData(List<GankData.ResultsBean> refreshData) {
        if (refreshData != null) {
            mData.addAll(refreshData);
            notifyDataSetChanged();
        }
    }

    public void removeAllData() {
        mData.clear();
        notifyDataSetChanged();
    }
}
