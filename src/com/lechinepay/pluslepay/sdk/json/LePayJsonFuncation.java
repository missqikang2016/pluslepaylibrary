package com.lechinepay.pluslepay.sdk.json;

import com.google.gson.Gson;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayAddBankCardResultBean;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayBankCardInfoBean;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayBankNameResultBean;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayQuickPayResultBean;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePaySendCodeResultBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现：
 * 作者：thomson King on 2016/8/10 0010 15:42
 * 邮箱：ouqikang@unionpay95516.com
 *
 */
public class LePayJsonFuncation {


    public static LePayQuickPayResultBean QuickPayResult(String json) {
        LePayQuickPayResultBean lePayQuickPayResultBean = new LePayQuickPayResultBean();
        Gson gson = new Gson();
        lePayQuickPayResultBean = gson.fromJson(json, LePayQuickPayResultBean.class);
        return lePayQuickPayResultBean;
    }


    public static LePayBankNameResultBean BankCardName(String json) {
        LePayBankNameResultBean lePayBankNameResultBean = new LePayBankNameResultBean();
        Gson gson = new Gson();
        lePayBankNameResultBean = gson.fromJson(json, LePayBankNameResultBean.class);
        return lePayBankNameResultBean;
    }

    public static LePayAddBankCardResultBean AddBankCard(String json) {

        LePayAddBankCardResultBean lePayAddBankCardResultBean = new LePayAddBankCardResultBean();
        Gson gson = new Gson();
        lePayAddBankCardResultBean = gson.fromJson(json, LePayAddBankCardResultBean.class);
        return lePayAddBankCardResultBean;

    }

    public static LePaySendCodeResultBean SendCode(String json) {
        LePaySendCodeResultBean lePaySendCodeResultBean = new LePaySendCodeResultBean();
        Gson gson = new Gson();
        lePaySendCodeResultBean = gson.fromJson(json, LePaySendCodeResultBean.class);
        return lePaySendCodeResultBean;
    }

    /**
     * 方法名:QureyCardsInfo 查询银行卡列表
     * 方法参数:json 字符串
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * @return List
     * @param json
     * FIXME
     */

    public static List<LePayBankCardInfoBean> QureyCardsInfo(String json) {

        List<LePayBankCardInfoBean> list = new ArrayList<LePayBankCardInfoBean>();
        try {

            JSONObject jsonObj = new JSONObject(json);

            if (jsonObj != null) {

                if (jsonObj.has("status") && jsonObj.getInt("status") == 1) {

                    if (jsonObj.has("result")) {

                        JSONArray jsonArray = jsonObj.getJSONArray("result");

                        if (jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = new JSONObject();

                                jsonObject = jsonArray.getJSONObject(i);

                                if (jsonObject.getInt("status") == 1) {

                                    LePayBankCardInfoBean lePayBankCardInfoBean = new LePayBankCardInfoBean();

                                    lePayBankCardInfoBean.setBankCardNumber(jsonObject.getString("bankCardNo"));

                                    lePayBankCardInfoBean.setBindId(jsonObject.getString("bindingId"));

                                    lePayBankCardInfoBean.setBuyerId(jsonObject.getString("buyerId"));

                                    lePayBankCardInfoBean.setCardHolder(jsonObject.getString("idName"));

                                    lePayBankCardInfoBean.setIdEntity(jsonObject.getString("idCardNo"));

                                    lePayBankCardInfoBean.setPhoneNumber(jsonObject.getString("mobile"));

                                    lePayBankCardInfoBean.setBankCardName(jsonObject.getString("bankName"));

                                    lePayBankCardInfoBean.setBankCardType(jsonObject.getString("dbcr"));

                                    list.add(lePayBankCardInfoBean);

                                }
                            }
                        }
                    }

                } else {
                    return null;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


}