package com.lechinepay.pluslepay.sdk.json.resultentity;

import java.io.Serializable;
import java.util.List;

/**
 * 实现：
 * 作者：thomson King on 2016/8/11 0011 16:12
 * 邮箱：ouqikang@unionpay95516.com
 */
public class LePayBankNameResultBean implements Serializable{



    private int status;
    private String code;
    private String message;

    private ResultBean result;
    private Object failures;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Object getFailures() {
        return failures;
    }

    public void setFailures(Object failures) {
        this.failures = failures;
    }

    public static class ResultBean {
        private Object errCode;
        private Object errMsg;


        private List<PayChannelListBean> payChannelList;

        public Object getErrCode() {
            return errCode;
        }

        public void setErrCode(Object errCode) {
            this.errCode = errCode;
        }

        public Object getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(Object errMsg) {
            this.errMsg = errMsg;
        }

        public List<PayChannelListBean> getPayChannelList() {
            return payChannelList;
        }

        public void setPayChannelList(List<PayChannelListBean> payChannelList) {
            this.payChannelList = payChannelList;
        }

        public static class PayChannelListBean {
            private String instId;
            private String instName;
            private String dbcr;
            private String companyPersonal;
            private String instCode;

            public String getInstId() {
                return instId;
            }

            public void setInstId(String instId) {
                this.instId = instId;
            }

            public String getInstName() {
                return instName;
            }

            public void setInstName(String instName) {
                this.instName = instName;
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
        }
    }

    @Override
    public String toString() {
        return "LePayBankNameResultBean{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", failures=" + failures +
                '}';
    }
}