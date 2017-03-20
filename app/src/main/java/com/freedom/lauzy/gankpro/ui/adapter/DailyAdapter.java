package com.freedom.lauzy.gankpro.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.function.constants.ValueConstants;
import com.freedom.lauzy.gankpro.function.entity.ItemBean;
import com.freedom.lauzy.gankpro.function.utils.TransitionUtils;
import com.freedom.lauzy.gankpro.ui.activity.GankDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.BOUNCE_ENTER_TYPE;
import static com.freedom.lauzy.gankpro.function.constants.ValueConstants.ImageValue.ENTER_TYPE;

/**
 * daily
 * Created by Lauzy on 2017/1/24.
 */

public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemBean> mItemBeen;
    private Context mContext;

    public DailyAdapter(Context context, List<ItemBean> itemBeen) {
        mItemBeen = itemBeen;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_daily_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemBean itemBean = mItemBeen.get(position);
       /* if (position == 0) {
            ((ViewHolder) holder).mTxtDailyCategory.setVisibility(View.VISIBLE);
        } else {
            boolean isCategoryVisible = mItemBeen.get(position - 1).getType().equals(mItemBeen.get(position).getType());
            ((ViewHolder) holder).mTxtDailyCategory.setVisibility(isCategoryVisible ? View.GONE : View.VISIBLE);
        }
        ((ViewHolder) holder).mTxtDailyCategory.setText(itemBean.getType());*/
        ((ViewHolder) holder).mTxtDailyContent.setText(itemBean.getDesc());
        ((ViewHolder) holder).mCvGankItem.setOnClickListener(gankDetailListener(itemBean));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] attrs = new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            typedArray.recycle();
            ((ViewHolder) holder).mCvGankItem.setForeground(ContextCompat.getDrawable(mContext, backgroundResource));
        }
    }

    private View.OnClickListener gankDetailListener(final ItemBean itemBean) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GankDetailActivity.class);
                intent.putExtra(ValueConstants.GANK_DETAIL, itemBean.getUrl());
//                mContext.startActivity(intent);
                intent.putExtra(ENTER_TYPE, BOUNCE_ENTER_TYPE);
                TransitionUtils.transitionTo((Activity) mContext,intent);
            }
        };
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
        /*@BindView(R.id.txt_daily_category)
        TextView mTxtDailyCategory;*/
        @BindView(R.id.txt_daily_content)
        TextView mTxtDailyContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
