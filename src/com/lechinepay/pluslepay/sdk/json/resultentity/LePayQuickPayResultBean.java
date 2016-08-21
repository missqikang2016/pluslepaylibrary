package com.lechinepay.pluslepay.sdk.json.resultentity;

import java.io.Serializable;

/**
 * 实现:
 * 作者：thomson King on 2016/8/12 0012 12:00
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayQuickPayResultBean implements Serializable{




    private String version;
    private String encoding;
    private String signature;
    private Object reqReserved;
    private String mchId;
    private Object subMchId;
    private Object agentMchId;
    private Object agentMchAppId;
    private Object companyId;
    private String cmpAppId;
    private String respCode;
    private String respMsg;
    private Object payChnlType;
    private String notifyToken;
    private Object tranStatus;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(Object reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public Object getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(Object subMchId) {
        this.subMchId = subMchId;
    }

    public Object getAgentMchId() {
        return agentMchId;
    }

    public void setAgentMchId(Object agentMchId) {
        this.agentMchId = agentMchId;
    }

    public Object getAgentMchAppId() {
        return agentMchAppId;
    }

    public void setAgentMchAppId(Object agentMchAppId) {
        this.agentMchAppId = agentMchAppId;
    }

    public Object getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object companyId) {
        this.companyId = companyId;
    }

    public String getCmpAppId() {
        return cmpAppId;
    }

    public void setCmpAppId(String cmpAppId) {
        this.cmpAppId = cmpAppId;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getPayChnlType() {
        return payChnlType;
    }

    public void setPayChnlType(Object payChnlType) {
        this.payChnlType = payChnlType;
    }

    public String getNotifyToken() {
        return notifyToken;
    }

    public void setNotifyToken(String notifyToken) {
        this.notifyToken = notifyToken;
    }

    public Object getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(Object tranStatus) {
        this.tranStatus = tranStatus;
    }

    @Override
    public String toString() {
        return "LePayQuickPayResultBean{" +
                "version='" + version + '\'' +
                ", encoding='" + encoding + '\'' +
                ", signature='" + signature + '\'' +
                ", reqReserved=" + reqReserved +
                ", mchId='" + mchId + '\'' +
                ", subMchId=" + subMchId +
                ", agentMchId=" + agentMchId +
                ", agentMchAppId=" + agentMchAppId +
                ", companyId=" + companyId +
                ", cmpAppId='" + cmpAppId + '\'' +
                ", respCode='" + respCode + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", payChnlType=" + payChnlType +
                ", notifyToken='" + notifyToken + '\'' +
                ", tranStatus=" + tranStatus +
                '}';
    }
}