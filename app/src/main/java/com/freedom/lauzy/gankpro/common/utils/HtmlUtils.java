package com.freedom.lauzy.gankpro.common.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * 处理Html格式的字符串
 * Created by Lauzy on 2017/2/21.
 */

public class HtmlUtils {
    /**
     * 转换html格式为Span
     * @param html html
     * @return result
     */
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
