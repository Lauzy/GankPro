package com.freedom.lauzy.gankpro.common.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 公共Adapter
 * Created by Lauzy on 2017/2/6.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RvViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mData;
    private int mLayoutId;
    // 多布局支持
    private MultiTypeSupport mMultiTypeSupport;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    /**
     * 多布局支持
     */
    public CommonAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        // 先inflate数据
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        RvViewHolder holder = new RvViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    protected abstract void convert(RvViewHolder holder, T item);

    @Override
    public int getItemCount() {
//        return mData == null ? 0 : mData.size();
        return mData.size();
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }
}
