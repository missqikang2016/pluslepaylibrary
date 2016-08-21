package com.lechinepay.pluslepay.sdk.Entity;

/**
 * 实现：支付返回JAVA Bean
 * 作者：thomson King on 2016/8/13 0013 13:57
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayResultEntity {

    private int respCode;
    private String respMsg;
    private String channel;
    private String channelCode;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "LePayResultEntity{" +
                "respCode=" + respCode +
                ", respMsg='" + respMsg + '\'' +
                ", channel='" + channel + '\'' +
                ", channelCode='" + channelCode + '\'' +
                '}';
    }
}