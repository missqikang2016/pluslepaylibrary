package com.lechinepay.pluslepay.manger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实现：activity 管理类
 * 作者：thomson King on 2016/8/16 0016 11:55
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayActivityManager extends Activity {

    private static Map<String, Activity> destoryMap = new HashMap<String, Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDestoryActivity(this, this.getLocalClassName());
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    private static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁所有Activity
     */
    public static void destoryActivity() {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {

            destoryMap.get(activityName).finish();
        }
    }
}