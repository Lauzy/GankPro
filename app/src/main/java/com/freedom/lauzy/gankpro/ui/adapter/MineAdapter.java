package com.freedom.lauzy.gankpro.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;
import com.freedom.lauzy.gankpro.common.widget.ImageLoader;
import com.freedom.lauzy.gankpro.function.net.NetConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lauzy on 2017/3/23.
 */

public class MineAdapter extends RecyclerView.Adapter {

    private static final int HEADER_TYPE = 0x0016;
    private static final int ITEM_TYPE = 0x0017;
    private Context mContext;

    public MineAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            return new HeadHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_mine_header_view, parent, false));
        } else {
            return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_mine_item_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder) {
            ImageLoader.loadImage(mContext, NetConstants.AVATAR_URL, ((HeadHolder) holder).mImgAvatar);
        } else if (holder instanceof ItemHolder) {
            TypedArray mineArr = mContext.getResources().obtainTypedArray(R.array.MineItemArr);
            ((ItemHolder) holder).mImgMineItem.setImageDrawable(mineArr.getDrawable(position - 1));
            mineArr.recycle();

            String[] mineContentArr = mContext.getResources().getStringArray(R.array.MineItemContentArr);
            ((ItemHolder) holder).mTxtItemContent.setText(mineContentArr[position - 1]);
            ((ItemHolder) holder).mSwitchMineItem.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            if (position == 2){
                ((ItemHolder) holder).mSwitchMineItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return ITEM_TYPE;
    }

    class HeadHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar)
        ImageView mImgAvatar;
        @BindView(R.id.txt_user)
        TextView mTxtUser;

        public HeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_mine_item)
        ImageView mImgMineItem;
        @BindView(R.id.txt_mine_item_content)
        TextView mTxtItemContent;
        @BindView(R.id.switch_mine_item)
        Switch mSwitchMineItem;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
