package com.freedom.lauzy.gankpro.common.widget;

import android.app.AlertDialog;
import android.content.Context;

/**
 * MyDialg
 * Created by Lauzy on 2017/2/8.
 */

public class LyDialog extends AlertDialog {
    protected LyDialog(Context context) {
        super(context);
    }

    protected LyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected LyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
