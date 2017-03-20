package com.freedom.lauzy.gankpro.function.constants;

/**
 * Created by Lauzy on 2017/3/9.
 */

public class ValueConstants {
    public static final String GANK_DETAIL = "gank_detail";

    /**
     * Created by Lauzy on 2017/1/23.
     */

    public class ImageValue {
        public static final String IMAGE_URL = "img_url";
        public static final String PUBLISH_DATE = "publish_date";
        public static final String PIC_DESC = "picture_desc";

        public static final String ENTER_TYPE = "enter_type";

        public static final int BOUNCE_ENTER_TYPE = 0x0011;  //动画结束的时候弹起
        public static final int ACCELERATE_DECELERATE_ENTER_TYPE = 0x0012;//在动画开始与结束的地方速率改变比较慢，在中间的时候加速
    }
}
