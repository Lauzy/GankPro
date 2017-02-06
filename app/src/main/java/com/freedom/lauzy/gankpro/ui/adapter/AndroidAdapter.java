package com.freedom.lauzy.gankpro.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.common.widget.adapter.CommonAdapter;
import com.freedom.lauzy.gankpro.common.widget.adapter.RvViewHolder;
import com.freedom.lauzy.gankpro.function.entity.GankData;

import java.util.List;

/**
 * Android
 * Created by Lauzy on 2017/2/4.
 */

public class AndroidAdapter extends CommonAdapter<GankData.ResultsBean>{

    public AndroidAdapter(Context context, List<GankData.ResultsBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void convert(RvViewHolder holder, GankData.ResultsBean item) {
        holder.setText(R.id.txt_android_content, item.getDesc());
        ImageView imageView = holder.getView(R.id.img_android);
        imageView.setVisibility(item.getImages() == null ? View.GONE : View.VISIBLE);
        if (item.getImages() != null) {
            ImageLoader.loadImage(mContext, item.getImages().get(0) + "?imageView2/0/w/100", imageView);
        }
    }

    public void addData(List<GankData.ResultsBean> refreshData,int headerCount) {
        if (refreshData != null) {
            mData.addAll(refreshData);
//            notifyDataSetChanged();
            notifyItemRangeInserted(mData.size() - refreshData.size() + headerCount, refreshData.size());
            int dataSize = mData == null ? 0 : mData.size();
            if (dataSize == refreshData.size()){
                notifyDataSetChanged();
            }
        }

    }

    public void removeAllData() {
        mData.clear();
        notifyDataSetChanged();
    }
}
