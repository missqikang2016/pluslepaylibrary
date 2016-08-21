package com.lechinepay.pluslepay.sdk.listener;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public interface LePaySendPayCallBack {

    void OnPayStart(int payType);

    void OnPayFinished(int payType,boolean isSuccess,String payResult);
}
