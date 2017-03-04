package com.freedom.lauzy.gankpro.common.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * 拼接字符串工具
 * Created by Lauzy on 2017/2/17.
 */

public class SpannableUtil {
    private SpannableString mSpannableString;

    public SpannableUtil(String content) {
        mSpannableString = new SpannableString(content);
    }


    public SpannableUtil setSize(int dpSize, int start, int end) {
        mSpannableString.setSpan(new AbsoluteSizeSpan(dpSize, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableUtil setColor(Context context, int colorRes, int start, int end) {
        mSpannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorRes)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableString getSpannableString() {
        return mSpannableString;
    }
}
