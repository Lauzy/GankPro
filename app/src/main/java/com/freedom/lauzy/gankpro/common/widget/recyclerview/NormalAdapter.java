package com.freedom.lauzy.gankpro.common.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * 基类
 * Created by Lauzy on 2017/2/4.
 */

public abstract class NormalAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected List<T> mData;
    private int mLayoutId;
    protected LayoutInflater mInflater;

    public NormalAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, mLayoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    protected abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
