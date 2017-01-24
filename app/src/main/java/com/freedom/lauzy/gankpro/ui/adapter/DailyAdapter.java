package com.freedom.lauzy.gankpro.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * daily
 * Created by Lauzy on 2017/1/24.
 */

public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemBean> mItemBeen;

    public DailyAdapter(List<ItemBean> itemBeen) {
        mItemBeen = itemBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daily_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBean itemBean = mItemBeen.get(position);
        if (position == 0) {
            ((ViewHolder) holder).mTxtDailyCategory.setVisibility(View.VISIBLE);
        } else {
            boolean isCategoryVisible = mItemBeen.get(position - 1).getType().equals(mItemBeen.get(position).getType());
            ((ViewHolder) holder).mTxtDailyCategory.setVisibility(isCategoryVisible ? View.GONE : View.VISIBLE);
        }
        ((ViewHolder) holder).mTxtDailyContent.setText(itemBean.getDesc());
        ((ViewHolder) holder).mTxtDailyCategory.setText(itemBean.getType());
    }

    @Override
    public int getItemCount() {
        return mItemBeen == null ? 0 : mItemBeen.size();
    }

    public void removeAllData() {
        mItemBeen.clear();
        notifyDataSetChanged();
    }

    public void addData(List<ItemBean> refreshData) {
        mItemBeen.addAll(refreshData);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_gank_item)
        CardView mCvGankItem;
        @BindView(R.id.txt_daily_category)
        TextView mTxtDailyCategory;
        @BindView(R.id.txt_daily_content)
        TextView mTxtDailyContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.cv_gank_item})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cv_gank_item:
                    break;
            }
        }
    }
}
