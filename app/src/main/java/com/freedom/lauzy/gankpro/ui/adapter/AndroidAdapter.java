package com.freedom.lauzy.gankpro.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter.CommonAdapter;
import com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter.RvViewHolder;
import com.freedom.lauzy.gankpro.function.constants.ValueConstants;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.freedom.lauzy.gankpro.ui.activity.GankDetailActivity;

import java.util.List;

/**
 * Android
 * Created by Lauzy on 2017/2/4.
 */

//public class AndroidAdapter extends CommonAdapter<GankData.ResultsBean>{
public class AndroidAdapter extends BaseQuickAdapter<GankData.ResultsBean, BaseViewHolder> {

    public AndroidAdapter(int layoutResId, List<GankData.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankData.ResultsBean item) {
        helper.setText(R.id.txt_android_content, item.getDesc());
        ImageView imageView = helper.getView(R.id.img_android);
        imageView.setVisibility(item.getImages() == null ? View.GONE : View.VISIBLE);
        if (item.getImages() != null) {
            ImageLoader.loadImage(mContext, item.getImages().get(0) + "?imageView2/0/w/100", imageView);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] attrs = new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            typedArray.recycle();
            helper.getView(R.id.layout_android_item).setForeground(ContextCompat.getDrawable(mContext, backgroundResource));
        }

        helper.getView(R.id.layout_android_item).setOnClickListener(getAndroidListener(item));
    }

    private View.OnClickListener getAndroidListener(final GankData.ResultsBean item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GankDetailActivity.class);
                intent.putExtra(ValueConstants.GANK_DETAIL, item.getUrl());
                intent.putExtra(ValueConstants.ImageValue.ENTER_TYPE,ValueConstants.ImageValue.ACCELERATE_DECELERATE_ENTER_TYPE);
//                mContext.startActivity(intent);
                TransitionUtils.transitionTo(((Activity) mContext), intent);
            }
        };
    }

   /* public AndroidAdapter(Context context, List<GankData.ResultsBean> data, int layoutId) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] attrs = new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            typedArray.recycle();
            holder.getView(R.id.layout_android_item).setForeground(ContextCompat.getDrawable(mContext, backgroundResource));
        }
    }*/
}
