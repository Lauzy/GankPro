package com.freedom.lauzy.gankpro.ui.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.utils.DensityUtils;
import com.freedom.lauzy.gankpro.function.entity.GankData;
import com.freedom.lauzy.gankpro.ui.activity.DailyActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.Random;

import static com.freedom.lauzy.gankpro.function.ValueConstants.ImageValue.IMAGE_URL;
import static com.freedom.lauzy.gankpro.function.ValueConstants.ImageValue.PIC_DESC;
import static com.freedom.lauzy.gankpro.function.ValueConstants.ImageValue.PUBLISH_DATE;

/**
 * BaseQuickAdapter方便添加header，footer，emptyView，以及加载更多
 * 设置多类型item时需数据设置itemType
 * Created by Lauzy on 2017/1/22.
 */

public class BeautyAdapter extends BaseQuickAdapter<GankData.ResultsBean, BaseViewHolder> {

    public BeautyAdapter(int layoutResId, List<GankData.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankData.ResultsBean item) {
        //(ImageView) helper.getView(R.id.img_beauty_item)
        Picasso.with(mContext).load(item.getUrl())
                .into((ImageView) helper.getView(R.id.img_beauty_item));
        /*ImageView imgItem = helper.getView(R.id.img_beauty_item);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (DensityUtils.dp2px(mContext, 200) - 100 + Math.random() * 50));
        imgItem.setLayoutParams(layoutParams);*/

        /*CollectionEntityDao entityDao = GankApp.getGankApp().getDaoSession().getCollectionEntityDao();
        CollectionEntity entity = new CollectionEntity((long) helper.getAdapterPosition(), item.getUrl(), item.getDesc(), item.getDesc());
        entityDao.insertOrReplace(entity);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] attrs = new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            typedArray.recycle();
            helper.getView(R.id.img_beauty_item).setForeground(ContextCompat.getDrawable(mContext, backgroundResource));
        }

        helper.getView(R.id.img_beauty_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DailyActivity.class);
                intent.putExtra(IMAGE_URL, item.getUrl());
                intent.putExtra(PUBLISH_DATE, item.getPublishedAt());
                intent.putExtra(PIC_DESC, item.getDesc());
                if (Build.VERSION.SDK_INT >= 21) {
                    mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, v, "transitionImg").toBundle());
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
