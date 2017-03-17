package com.freedom.lauzy.gankpro.function.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lauzy on 2017/3/17.
 */

public class SDCardUtils {

    public static String getDownloadPath(Context context) {
        String dirPath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dirPath = context.getExternalFilesDir(null).getAbsolutePath();
        } else {
            dirPath = context.getFilesDir().getAbsolutePath();
        }
        return dirPath + File.separator + "GankPic";//文件夹
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param fileName
     */
    public static void saveImage(Context context, Bitmap bitmap, String fileName) {

        BufferedOutputStream bos = null;
        try {
            File dir = new File(getDownloadPath(context));
            if (!dir.exists()) {
                dir.mkdir();
            }
            File imageFile = new File(getDownloadPath(context) + File.separator + fileName);
            bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
