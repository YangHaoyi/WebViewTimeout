package com.yanghaoyi.webviewtimeout.presenter.util;

import android.util.Log;

import com.yanghaoyi.webviewtimeout.constants.ManualConstants;


/**
 * @author : YangHaoYi on 2020/7/1.
 * Email  :  yang.haoyi@qq.com
 * Description : 驾驶行为分析工具类
 * Change : YangHaoYi on 2020/7/1.
 * Version : V 1.0
 */
public class ManualLogUtil {

    /** 日志输出开关 */
    private static boolean openLogMessage = true;
    /** 写文件开关 */
    private static boolean writeToFile = false;

    /**
     * 日志输出
     * @param message 日志信息
     * **/
    public static void logMessage(String message){
        if(openLogMessage){
            Log.d(ManualConstants.TAG, message);
        }
    }
}
