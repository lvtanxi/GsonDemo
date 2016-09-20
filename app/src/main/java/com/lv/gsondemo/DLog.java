package com.lv.gsondemo;


import com.orhanobut.logger.Logger;

/**
 *日志打印
 */
public class DLog {

    private static boolean sDebug;

    public static void init(String tag, boolean debug){
        Logger.init(tag);
        sDebug=debug;
    }

    public static void e(Object message, Object... args) {
        if (sDebug) {
            if(null==message)message="打印了空消息";
            Logger.e(message.toString(), args);
        }
    }
    public static void d(Object message, Object... args) {
        if (sDebug){
            if(null==message)message="打印了空消息";
            Logger.d(message.toString(), args);
        }
    }
}
