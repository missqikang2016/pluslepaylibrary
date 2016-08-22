package com.lechinepay.pluslepay.sdk.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.lechinepay.pluslepay.sdk.listener.ChannelsPayListener;

import java.io.Serializable;

/**
 * 实现：
 * 作者：thomson King on 2016/8/13 0013 13:32
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayOrderInfoEntity implements Serializable {

    private String respCode;
    private String respMsg;
    private String tradeNo;
    private String amount;
    private String appOrderInfo;
    private String startTime;
    private String payChnlType;
    private String summary;
    private int payType;
    private String buyerId;
    private boolean isSuccess;
    private String token;
    private String chanleTrNo;
    private String productCode;
    private int cardType;
    private String bankCardNo;//(加密)
    private String idCardNo;//(加密)
    private String mobile;
    private String idName;
    private String cardYear;
    private String cardMonth;
    private String cvNum;
    private String bankCardName;
    private String bindId;
    private String instId;
    private String dbcr;
    private String companyPersonal;
    private String instCode;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getDbcr() {
        return dbcr;
    }

    public void setDbcr(String dbcr) {
        this.dbcr = dbcr;
    }

    public String getCompanyPersonal() {
        return companyPersonal;
    }

    public void setCompanyPersonal(String companyPersonal) {
        this.companyPersonal = companyPersonal;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getCardYear() {
        return cardYear;
    }

    public void setCardYear(String cardYear) {
        this.cardYear = cardYear;
    }

    public String getCardMonth() {
        return cardMonth;
    }

    public void setCardMonth(String cardMonth) {
        this.cardMonth = cardMonth;
    }

    public String getCvNum() {
        return cvNum;
    }

    public void setCvNum(String cvNum) {
        this.cvNum = cvNum;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChanleTrNo() {
        return chanleTrNo;
    }

    public void setChanleTrNo(String chanleTrNo) {
        this.chanleTrNo = chanleTrNo;
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
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

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppOrderInfo() {
        return appOrderInfo;
    }

    public void setAppOrderInfo(String appOrderInfo) {
        this.appOrderInfo = appOrderInfo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPayChnlType() {
        return payChnlType;
    }

    public void setPayChnlType(String payChnlType) {
        this.payChnlType = payChnlType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "LePayOrderInfoEntity{" +
                "respCode='" + respCode + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", amount='" + amount + '\'' +
                ", appOrderInfo='" + appOrderInfo + '\'' +
                ", startTime='" + startTime + '\'' +
                ", payChnlType='" + payChnlType + '\'' +
                ", summary='" + summary + '\'' +
                ", payType=" + payType +
                ", buyerId='" + buyerId + '\'' +
                ", isSuccess=" + isSuccess +
                ", token='" + token + '\'' +
                ", chanleTrNo='" + chanleTrNo + '\'' +
                ", productCode='" + productCode + '\'' +
                ", cardType=" + cardType +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idName='" + idName + '\'' +
                ", cardYear='" + cardYear + '\'' +
                ", cardMonth='" + cardMonth + '\'' +
                ", cvNum='" + cvNum + '\'' +
                ", bankCardName='" + bankCardName + '\'' +
                ", bindId='" + bindId + '\'' +
                ", instId='" + instId + '\'' +
                ", dbcr='" + dbcr + '\'' +
                ", companyPersonal='" + companyPersonal + '\'' +
                ", instCode='" + instCode + '\'' +
                '}';
    }
}