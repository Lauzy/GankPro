package com.freedom.lauzy.gankpro.common.widget.recyclerview.adapter;

/**
 * 多布局
 * Created by Lauzy on 2017/2/6
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    int getLayoutId(T item, int position);
}
