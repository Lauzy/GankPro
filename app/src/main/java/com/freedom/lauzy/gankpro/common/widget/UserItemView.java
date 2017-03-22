package com.freedom.lauzy.gankpro.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freedom.lauzy.gankpro.R;


/**
 * 用户个人中心条目
 * Created by Lauzy on 2017/2/10.
 */

public class UserItemView extends LinearLayout {
    private ImageView mImgItemIcon;
    private TextView mTxtItemName;
    private TextView mTxtTip;
//    private ImageView mImgItemMore;
//    private boolean mItemMore;

    public UserItemView(Context context) {
        super(context);
    }

    public UserItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
        initAttrs(attrs);
    }

    public UserItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TintTypedArray array = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.UserSettingItemView);
        Drawable itemIcon = array.getDrawable(R.styleable.UserSettingItemView_itemIcon);
        String itemName = array.getString(R.styleable.UserSettingItemView_itemName);
        String itemTip = array.getString(R.styleable.UserSettingItemView_itemTip);
//        mItemMore = array.getBoolean(R.styleable.UserSettingItemView_itemMore, false);

        mImgItemIcon.setImageDrawable(itemIcon);
        mTxtItemName.setText(itemName);
        mTxtTip.setText(itemTip);
    }

    private void initViews(Context context) {
        View itemView = View.inflate(context, R.layout.layout_user_setting_item, this);
        mImgItemIcon = ((ImageView) itemView.findViewById(R.id.img_mine_item_icon));
        mTxtItemName = (TextView) itemView.findViewById(R.id.txt_mine_item_name);
    }
}
