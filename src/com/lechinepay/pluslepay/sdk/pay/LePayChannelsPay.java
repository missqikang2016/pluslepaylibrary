package com.lechinepay.pluslepay.sdk.pay;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.sdk.Entity.AliPayResultBean;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.activity.lepayactivity.LePayPayMentCardListActivity;
import com.lechinepay.pluslepay.sdk.activity.lepayactivity.LePayPaymentInputCardNumberActivity;
import com.lechinepay.pluslepay.sdk.activity.lepayactivity.LePayPaymentResultActivity;
import com.lechinepay.pluslepay.sdk.json.LePayJsonFuncation;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayBankCardInfoBean;
import com.lechinepay.pluslepay.sdk.listener.ChannelsPayListener;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.lechinepay.pluslepaytoolsdk.controller.LePayControllerManage;
import com.socks.library.KLog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现：分渠道进行支付，其中包括支付宝支付，微信支付，银行卡快捷支付
 * 作者：thomson King on 2016/8/13 0013 21:13
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayChannelsPay {

    private static ChannelsPayListener mChannelsPayListener;

    private static LePayOrderInfoEntity mLePayOrderInfoEntity;

    private static Activity mActivity;

    private static List<LePayBankCardInfoBean> bankCardslist = new ArrayList<LePayBankCardInfoBean>();

    private static Dialog dialog;




    public static void sendChannelsPay(final Activity activity, LePayOrderInfoEntity lePayOrderInfoEntity, ChannelsPayListener channelsPayListener) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = LePayTools.createLoadingDialog(activity,"");
            }
        });

        mChannelsPayListener = channelsPayListener;

        LePayConfigure.CHANNELSPAYLISTENNER = mChannelsPayListener;

        mActivity = activity;

        mLePayOrderInfoEntity = lePayOrderInfoEntity;

        LePayConfigure.payInfo = lePayOrderInfoEntity;

        new SendPayAsyncTask().execute(lePayOrderInfoEntity.getAppOrderInfo(), lePayOrderInfoEntity.getPayChnlType());


    }


    static class SendPayAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            mChannelsPayListener.OnChannelsPayStart();

            String result = null;

            if (params[1].equals(LePayConfigure.ALIPAY)) {

                result = sendAliPay(params[0]);

            } else if (params[1].equals(LePayConfigure.WECARTPAY)) {

                sendWeCartPay(params[0]);
            }else if (params[1].equals(LePayConfigure.QUICKPAY)){

                sendQuickPay();

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result!=null)
            mChannelsPayListener.OnChannelsPayEnd(result);

        }
    }


    private static String sendAliPay(String orderInfo) {

        String res = null;
        boolean isSuccess = false;
        String status = "";
        try {
            PayTask alipay = new PayTask(mActivity);
            String result = alipay.pay(orderInfo, true);
            AliPayResultBean payResult = new AliPayResultBean(result); // 支付宝返回数据格式化解析
            String resultStatus = payResult.getResultStatus();
            status = resultStatus;
            Log.d("11111sendAliPay", "sendAliPay: " + resultStatus);
            res = LePayTools.setResultInfo(resultStatus,mLePayOrderInfoEntity.getPayChnlType(), LePayTools.checkAliPayResult(resultStatus), payResult.getMemo()); //对返回结果进行格式化以返回给开发者
            if (LePayTools.checkAliPayResult(resultStatus) == 0){
                isSuccess = true;
            }else if(LePayTools.checkAliPayResult(resultStatus) == 0){

            }else{
                isSuccess = false;
            }
        } catch (Exception e) { //Payment is Exception
            e.printStackTrace();
            res = LePayTools.setResultInfo(status,mLePayOrderInfoEntity.getPayChnlType(), -1, e.getMessage());
        }
        if (!status.equals("6001")){

            mLePayOrderInfoEntity.setSuccess(isSuccess);
            Bundle bundle = new Bundle();
            bundle.putSerializable("payInfo", mLePayOrderInfoEntity);
            LePayTools.GotoActivityByBundle(mActivity, LePayPaymentResultActivity.class,bundle);

        }



        return res;
    }


    private static void sendWeCartPay(String orderInfo) {

        PayReq payReq = LePayTools.checkWeCartResult(orderInfo);
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mActivity.getApplicationContext(), null);
        msgApi.registerApp(payReq.appId);
        msgApi.sendReq(payReq);
    }

    private static void sendQuickPay(){

        Bundle bundle = new Bundle();
        bundle.putSerializable("payInfo", mLePayOrderInfoEntity);
        if (mLePayOrderInfoEntity.getBuyerId() != null && !mLePayOrderInfoEntity.getBuyerId().equals("")){

            new InitInfoAsyncTask().execute();

        }else{

            LePayTools.GotoActivityByBundle(mActivity,LePayPaymentInputCardNumberActivity.class,bundle);

        }


    }

    private static class InitInfoAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (dialog!=null&&!dialog.isShowing()){

                        dialog.show();

                    }
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            LePayControllerManage lePayControllerManage = new LePayControllerManage();

                String result = lePayControllerManage.queryQuickPaymentBankCards(LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, mLePayOrderInfoEntity.getBuyerId());

                return result;

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (dialog!=null&&dialog.isShowing()){

                        dialog.cancel();

                    }
                }
            });

            if (result != null) {

                KLog.d(result);

                bankCardslist = LePayJsonFuncation.QureyCardsInfo(result);
                if (bankCardslist!=null){

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("payInfo", mLePayOrderInfoEntity);



                    if (bankCardslist.isEmpty()){

                        LePayTools.GotoActivityByBundle(mActivity,LePayPaymentInputCardNumberActivity.class,bundle);

                    }else{

                        LePayTools.GotoActivityByBundle(mActivity, LePayPayMentCardListActivity.class,bundle);

                    }

                }else{

                    Toast.makeText(mActivity, "参数错误", Toast.LENGTH_LONG).show();

                }





            }
        }
    }


}