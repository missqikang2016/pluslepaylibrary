package com.lechinepay.pluslepay.sdk.json.resultentity;

/**
 * 实现：
 * 作者：thomson King on 2016/8/11 0011 17:35
 * 邮箱：ouqikang@unionpay95516.com
 */
public class LePayAddBankCardResultBean {



    private int status;
    private Object code;
    private String message;
    private boolean result;
    private Object failures;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Object getFailures() {
        return failures;
    }

    public void setFailures(Object failures) {
        this.failures = failures;
    }
}