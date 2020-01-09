package com.julongsoft.measure.utils;

import android.os.Environment;

import com.julongsoft.measure.global.GlobalApplication;

import java.io.File;
import java.util.Locale;

/**
 * Created by Tao on 2016/5/26.
 */
public class SDCardUtil {

    private static String TAG = SDCardUtil.class.getSimpleName();

    /**
     * 跟路径
     */
    public static final String ROOT_PATH = String.format(Locale.US, "%s%s", getESDString(), "/Android/data/" + GlobalApplication.getContext().getPackageName() + "/");

    /**
     * 程序crash，crash文件的存储路径
     */
    public static final String APPLICATION_CRASH_PATH = String.format(Locale.US, "%s%s", ROOT_PATH, "crash/");


    /**
     * 判断sd卡是否有效
     *
     * @return
     */
    public static boolean getExternalStorageCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取sd卡的路径
     *
     * @return
     */
    public static String getESDString() {
        return getESD().toString();
    }

    /**
     * 获取sd卡文件路径
     *
     * @return
     */
    public static File getESD() {
        return Environment.getExternalStorageDirectory();
    }

    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
