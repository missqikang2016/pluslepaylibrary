package com.lechinepay.pluslepay.configure;

import android.content.Context;

import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.listener.ChannelsPayListener;

/**
 * 实现：
 * 作者：thomson King on 2016/8/12 0012 17:55
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayConfigure {

    public static String LEPAY_MCHID = null; // 用户商户号
    public static String LEPAY_CMPAPPID = null; // 用户App号
    public static Context LEPAY_CONTEXT = null; //
    public static String WECARTPAY = "wxpay"; // This is the WeChat channel type number
    public static String QUICKPAY = "chanpay"; // This is the WeChat channel type number
    public static String ALIPAY = "alipay"; // This is the AliPay channel type number
    public static LePayOrderInfoEntity payInfo;
    public static ChannelsPayListener CHANNELSPAYLISTENNER = null;
    public static boolean ISINITCONTROLLER = false;


}