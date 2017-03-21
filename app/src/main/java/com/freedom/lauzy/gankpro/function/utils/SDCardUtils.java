package com.freedom.lauzy.gankpro.function.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * SD卡
 * Created by Lauzy on 2017/3/17.
 */
@SuppressWarnings("all")
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

    /**
     * 判断SD卡是否已经挂载成功
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        // 通过Environment获取当前SD卡的状态
        String state = Environment.getExternalStorageState();
        // 如果当前SD卡状态与MEDIA_MOUNTED相同，则表示SD卡已经挂载成功
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 返回SD卡的根路径
     *
     * @return 如果SD卡处于挂载状态则返回SD卡根路径，否则返回null
     */
    public static String getSDCardRootDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }

    /**
     * @return 返回SD卡的总容量
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardTotalSize() {
        if (isSDCardMounted()) {
            // 簇StatFs Statistic File System文件系统统计

            StatFs sf = new StatFs(getSDCardRootDir()); // 获取对SD卡根路径的统计
            // 先获取当前系统中，每一簇所代表大小
            int blockSize = sf.getBlockSize();
//			long blockSize = sf.getBlockSizeLong();
            // 然后获取SD卡中有多少簇
            int blockCount = sf.getBlockCount();
//			long blockCount = sf.getBlockCountLong(); //需要api18

            return blockSize * blockCount / 1024 / 1024; // 以兆的单位返回
        }
        return 0;
    }

    /**
     * @return 返回SD卡的可用容量
     */
    @SuppressWarnings("deprecation")
    public static long getSDCardAvailableSize() {
        if (isSDCardMounted()) {
            // 簇StatFs Statistic File System文件系统统计

            StatFs sf = new StatFs(getSDCardRootDir()); // 获取对SD卡根路径的统计
            // 先获取当前系统中，每一簇所代表大小
            int blockSize = sf.getBlockSize();
            // 然后获取SD卡中可用簇的个数
            int blockCount = sf.getAvailableBlocks();

            return blockSize * blockCount / 1024 / 1024; // 以兆的单位返回
        }
        return 0;
    }

    /**
     * 向SD卡中9大公有目录保存数据
     *
     * @param data     需要保存的数据
     * @param type     9大公有目录的类型
     * @param fileName 需要保存的文件名称
     */
    public static void saveFileToPublicDir(byte[] data, String type, String fileName) {

        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            try {

                File fileDir = Environment.getExternalStorageDirectory();
                File file = new File(fileDir, fileName);

                if (!file.exists()) {
                    file.createNewFile();
                }

                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();

//                return true;
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
//        return false;
    }

    /**
     * 向SD卡中私有File目录写入数据
     *
     * @param context  上下文
     * @param data     需要保存的数据
     * @param type     私有File目录的类型
     * @param fileName 需要保存的文件名称
     * @return
     */
    public static void saveFileToExternalFileDir(Context context,
                                                    byte[] data, String type, String fileName) {

        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            try {
                File fileDir = context.getExternalFilesDir(type);
                File file = new File(fileDir, fileName);

                if (!file.exists()) {
                    file.createNewFile();
                }

                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();

//                return true;
            } catch (Exception e) {
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
//        return false;
    }


    /**
     * 向SD卡中私有Cache目录写入数据
     *
     * @param context  上下文
     * @param data     需要保存的数据
     * @param fileName 需要保存的文件名称
     * @return
     */
    public static void saveFileToExternalCacheDir(Context context,
                                                     byte[] data, String fileName) {
        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            try {
                File fileDir = context.getExternalCacheDir();
                File file = new File(fileDir, fileName);

                if (!file.exists()) {
                    file.createNewFile();
                }

                bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(data);
                bos.flush();

//                return true;
            } catch (Exception e) {
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
//        return false;
    }


    /**
     * 从SD卡中读取数据
     *
     * @param fileName 读取文件的绝对路径
     * @return
     */
    public static byte[] loadByteFromSDCard(String fileName) {

        if (isSDCardMounted()) {

            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                bis = new BufferedInputStream(new FileInputStream(fileName));

                byte[] buffer = new byte[1024 * 8];
                int len = 0;
                while ((len = bis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }

                return baos.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
