package com.lechinepay.pluslepay.tools;
import android.util.Log;

import com.socks.library.KLog;

import java.util.StringTokenizer;

/**
 * 实现：日志打印类
 * 作者：thomson King on 2016/8/15 0015 09:06
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayLogTool
{

    private LePayLogTool()
    {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "LePayLog";


    public static void initLePayLogTool (boolean is) { // 是否需要打印bug，可以在application的onCreate函数里面初始化

        LePayLogTool.isDebug = is;

    }

    public static void i(String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);

    }

    public static void d(String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);

    }

    public static void e(String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void v(String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void i(String tag, String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void e(String tag, String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void v(String tag, String msg)
    {
        if (isDebug)
            logWithMethod(new Exception(),msg);
    }

    public static void logWithMethod(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0) {
            KLog.i("error", "log: get trace info failed");
        }
        String class_name = getSimpleClassName(trace[0].getClassName());
        KLog.i(class_name, TAG + ": " + trace[0].getMethodName()
                + ":" + trace[0].getLineNumber());
    }

    public static void logWithMethod(Exception e, String msg) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0) {
            KLog.i("error", "log: get trace info failed");
        }
        String class_name = getSimpleClassName(trace[0].getClassName());
        KLog.i(class_name, TAG + ": " + trace[0].getMethodName()
                + ":" + trace[0].getLineNumber() + ": " + msg);
    }

    public static String getSimpleClassName(String fullClassName) {
        String split = ".";
        String class_name = "";
        StringTokenizer token = new StringTokenizer(fullClassName, split);
        while (token.hasMoreTokens()) {
            class_name = token.nextToken();
        }
        return class_name;
    }
}