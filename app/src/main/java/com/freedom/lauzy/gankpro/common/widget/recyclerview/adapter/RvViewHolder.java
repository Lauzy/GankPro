package com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 公共ViewHolder
 * Created by Lauzy on 2017/2/6.
 */

public class RvViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public RvViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 设置TextView文本
     */
    public RvViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 通过id获取view
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找
        View view = mViews.get(viewId);
        if (view == null) {
            // 直接从ItemView中找
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置View的Visibility
     */
    public RvViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置ImageView的资源
     */
    public RvViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置条目点击事件
     */
    public void setOnIntemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * 设置条目长按事件
     */
    public void setOnIntemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    /**
     * 设置图片通过路径,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
     * 也可以直接写死
     */
    public RvViewHolder setImageByUrl(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        if (imageLoader == null) {
            throw new NullPointerException("imageLoader is null!");
        }
        imageLoader.displayImage(imageView.getContext(), imageView, imageLoader.getImagePath());
        return this;
    }

    /**
     * 图片加载，这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
     * 也可以不写这个类
     */
    public static abstract class HolderImageLoader {
        private String mImagePath;

        public HolderImageLoader(String imagePath) {
            this.mImagePath = imagePath;
        }

        public String getImagePath() {
            return mImagePath;
        }

        public abstract void displayImage(Context context, ImageView imageView, String imagePath);
    }
}
