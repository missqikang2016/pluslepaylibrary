package com.lechinepay.pluslepay.sdk.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.Entity.LePayResultEntity;
import com.lechinepay.pluslepay.sdk.listener.ChannelsPayListener;
import com.lechinepay.pluslepay.sdk.listener.LePaySendPayCallBack;
import com.lechinepay.pluslepay.sdk.pay.LePayChannelsPay;
import com.lechinepay.pluslepay.tools.LePayLogTool;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.socks.library.KLog;

/**
 * +LePay控制器类
 * 实现：提供给用户进行支付接口调用，封装了initLePayController方法进行初始化控制器，
 * lePaySendPay方法进行支付调用，该方法中对开发者调用时的渠道选择进行判断并调用渠道接口
 * 作者：thomson King on 2016/8/12 0012 17:45
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayController {

    /**
     * 方法名:initLePayController 传入MCHID和CMPAPPID进行初始化
     * 方法参数: MCHID，CMPAPPID
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * @return boolean
     * FIXME
     */

    public static boolean initLePayController(Context context, String MCHID, String CMPAPPID, boolean isDeBug) {

        boolean isInit = false;
        LePayConfigure.LEPAY_MCHID = MCHID;
        LePayConfigure.LEPAY_CMPAPPID = CMPAPPID;
        LePayConfigure.LEPAY_CONTEXT = context;
        KLog.init(isDeBug);
        try {
            LePayTools.saveToSDCard();
            isInit = true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e("LePaySDK", "初始化失败");
        }
        LePayConfigure.ISINITCONTROLLER = isInit;
        return isInit;
    }

    /**
     * 方法名:lePaySendPay 开发者调用此方法实现支付宝支付，微信支付和银行卡快捷支付。
     * 方法参数:activity，Charge，BuyerId，lePaySendPayCallBack
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * @param activity 调用的Activity
     * @param Charge 订单信息
     * @param BuyerId 用户id
     * @param lePaySendPayCallBack
     * FIXME
     */

    public static void lePaySendPay(Activity activity, String Charge, String BuyerId, final LePaySendPayCallBack lePaySendPayCallBack) {


        if (LePayConfigure.ISINITCONTROLLER) {

            final LePayOrderInfoEntity lePayOrderInfoEntity = LePayTools.requestCheck(Charge);

            KLog.d(Charge);

            if (lePayOrderInfoEntity != null) {

                if (lePayOrderInfoEntity.getRespCode() != null){

                    if (lePayOrderInfoEntity.getRespCode().equals("000000")) {

                        lePayOrderInfoEntity.setBuyerId(BuyerId);

                        LePayChannelsPay.sendChannelsPay(activity, lePayOrderInfoEntity, new ChannelsPayListener() {


                            @Override
                            public void OnChannelsPayStart() {

                                lePaySendPayCallBack.OnPayStart(lePayOrderInfoEntity.getPayType());

                                KLog.d("PayType=" + lePayOrderInfoEntity.getPayType() + ",支付开始");

                            }

                            @Override
                            public void OnChannelsPayEnd(String result) {

                                Gson gson = new Gson();

                                LePayResultEntity lePayResultEntity = gson.fromJson(result, LePayResultEntity.class);

                                if (lePayResultEntity.getRespCode() == 0) {

                                    lePaySendPayCallBack.OnPayFinished(lePayOrderInfoEntity.getPayType(), true, result);

                                } else {

                                    lePaySendPayCallBack.OnPayFinished(lePayOrderInfoEntity.getPayType(), false, result);

                                }

                                KLog.d("支付结束，支付结果=" + result);

                            }
                        });


                    } else {

                        lePaySendPayCallBack.OnPayFinished(lePayOrderInfoEntity.getPayType(), false, LePayTools.setResultInfo("-1", lePayOrderInfoEntity.getPayChnlType(), -1, lePayOrderInfoEntity.getRespMsg()));
                        KLog.d("支付异常，" + lePayOrderInfoEntity.getRespMsg());
                    }

                }else{

                    lePaySendPayCallBack.OnPayFinished(3, false, LePayTools.setResultInfo("999", "获取失败", 999, "Charge异常"));
                    KLog.d("支付异常，Charge异常");

                }


            } else {
                lePaySendPayCallBack.OnPayFinished(3, false, LePayTools.setResultInfo("999", "获取失败", 999, "Charge异常"));
                KLog.d("支付异常，Charge异常");

            }

        } else {

            lePaySendPayCallBack.OnPayFinished(3, false, LePayTools.setResultInfo("888", "环境异常", 888, "初始化失败"));
            KLog.d("初始化失败，请检查权限");

        }


    }


}