package com.lechinepay.pluslepay.sdk.Entity;

import java.io.Serializable;

/**
 * Created by Thomson King on 2016/7/25 0025.
 */
public class LePayBankCardInfoEntity implements Serializable {

    private String bankCardNumber; // 银行卡号
    private String bankCardName; // 银行卡名称
    private String bankCardType; // 银行卡类别
    private String cardHolder; // 持卡人
    private String idEntity; // 身份证号
    private String creditCardValidity; // 身份证有效期
    private String cardBackNumber; // 卡背三位数字
    private String phoneNumber; // 预留手机号码
    private String bindId; //银行卡绑定标识
    private String bankCardImageUrl; //银行卡图片地址
    private String buyerId; //用戶id

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    public String getCreditCardValidity() {
        return creditCardValidity;
    }

    public void setCreditCardValidity(String creditCardValidity) {
        this.creditCardValidity = creditCardValidity;
    }

    public String getCardBackNumber() {
        return cardBackNumber;
    }

    public void setCardBackNumber(String cardBackNumber) {
        this.cardBackNumber = cardBackNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getBankCardImageUrl() {
        return bankCardImageUrl;
    }

    public void setBankCardImageUrl(String bankCardImageUrl) {
        this.bankCardImageUrl = bankCardImageUrl;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public String toString() {
        return "LePayBankCardInfoBean{" +
                "bankCardNumber='" + bankCardNumber + '\'' +
                ", bankCardName='" + bankCardName + '\'' +
                ", bankCardType=" + bankCardType +
                ", cardHolder='" + cardHolder + '\'' +
                ", idEntity='" + idEntity + '\'' +
                ", creditCardValidity='" + creditCardValidity + '\'' +
                ", cardBackNumber='" + cardBackNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bindId='" + bindId + '\'' +
                ", bankCardImageUrl='" + bankCardImageUrl + '\'' +
                ", buyerId='" + buyerId + '\'' +
                '}';
    }
}
