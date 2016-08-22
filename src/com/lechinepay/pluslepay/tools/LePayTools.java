package com.lechinepay.pluslepay.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.Entity.LePayResultEntity;
import com.tencent.mm.sdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现：
 * 作者：thomson King on 2016/8/13 0013 13:31
 * 邮箱：ouqikang@unionpay95516.com
 */

public class LePayTools {

    /**
     * 方法名:saveToSDCard 将文件写入sd卡中
     * 方法参数: 无
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static void saveToSDCard() throws Throwable {
        InputStream inStream = LePayConfigure.LEPAY_CONTEXT.getResources().openRawResource(R.raw.testserver);

        File file = new File(Environment.getExternalStorageDirectory()+ "/LePayFile");
        if (file.exists()){

        }else{
            file.mkdir();
            file = new File(Environment.getExternalStorageDirectory()+"/LePayFile/", "config.cer");
            FileOutputStream fileOutputStream = new FileOutputStream(file);//存入SDCard
            byte[] buffer = new byte[10];
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int len = 0;
            while((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] bs = outStream.toByteArray();
            fileOutputStream.write(bs);
            outStream.close();
            inStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        }

    }

    /**
     * 方法名: requestCheck
     * 方法参数:Charge 客户服务器获取到的Charge
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static LePayOrderInfoEntity requestCheck(String Charge) {
        LePayOrderInfoEntity lePayOrderInfoEntity = new LePayOrderInfoEntity();
        if (Charge != null && !Charge.equals("") && Charge.contains("{")) {
            try {
                JSONObject CHARGEJSON = new JSONObject(Charge);
                if (CHARGEJSON.has("respMsg"))
                    lePayOrderInfoEntity.setRespMsg(CHARGEJSON.getString("respMsg"));
                if (CHARGEJSON.has("respCode"))
                    lePayOrderInfoEntity.setRespCode(CHARGEJSON.getString("respCode"));
                if (CHARGEJSON.has("amount"))
                    lePayOrderInfoEntity.setAmount(CHARGEJSON.getString("amount"));
                if (CHARGEJSON.has("appOrderInfo"))
                    lePayOrderInfoEntity.setAppOrderInfo(CHARGEJSON.getString("appOrderInfo").replace("\\", ""));
                if (lePayOrderInfoEntity.getAppOrderInfo()!=null)
                    lePayOrderInfoEntity.setToken(getparams("token",lePayOrderInfoEntity.getAppOrderInfo(),CHARGEJSON.getString("payChnlType")));
                if (lePayOrderInfoEntity.getAppOrderInfo()!=null)
                    lePayOrderInfoEntity.setProductCode(getparams("productCode",lePayOrderInfoEntity.getAppOrderInfo(),CHARGEJSON.getString("payChnlType")));
                if (CHARGEJSON.has("startTime"))
                    lePayOrderInfoEntity.setStartTime(getFormatDate(CHARGEJSON.getString("startTime")));
                if (CHARGEJSON.has("summary"))
                    lePayOrderInfoEntity.setSummary(CHARGEJSON.getString("summary"));
                if (CHARGEJSON.has("outTradeNo"))
                    lePayOrderInfoEntity.setTradeNo(CHARGEJSON.getString("outTradeNo"));
                if (CHARGEJSON.has("payChnlType")) {
                    lePayOrderInfoEntity.setPayChnlType(CHARGEJSON.getString("payChnlType"));
                    if (CHARGEJSON.getString("payChnlType").length() > 0){
                        if (CHARGEJSON.getString("payChnlType").equals(LePayConfigure.ALIPAY)) {
                            lePayOrderInfoEntity.setPayType(1);
                        }
                        if (CHARGEJSON.getString("payChnlType").equals(LePayConfigure.WECARTPAY)) {
                            lePayOrderInfoEntity.setPayType(2);
                        }
                        if (CHARGEJSON.getString("payChnlType").equals(LePayConfigure.QUICKPAY)) {
                            lePayOrderInfoEntity.setPayType(3);
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
                lePayOrderInfoEntity = null;
            }
        } else {
            lePayOrderInfoEntity = null;
        }
        return lePayOrderInfoEntity;
    }

    /**
     * 实现:格式化返回数据
     * 方法名:setResultInfo
     * 方法参数:channelCode，Channel，status，msg
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static String setResultInfo(String channelCode,String Channel,int status,String msg){
        LePayResultEntity lePayResultEntity = new LePayResultEntity();
        Gson gson = new Gson();
        String result = null;
        lePayResultEntity.setRespCode(status);
        lePayResultEntity.setChannelCode(channelCode);
        lePayResultEntity.setRespMsg(msg);
        lePayResultEntity.setChannel(Channel);
        result = gson.toJson(lePayResultEntity); // 转换为JSON格式用于返回
        return result;
    }

    /**
     * 方法名:checkAliPayResult 支付结果格式化
     * 方法参数: resultStatus
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static int checkAliPayResult(String resultStatus) {
        int res = -1;

        if (resultStatus.equals("9000")) {  //Payment success
            res = 0;
        }else if(resultStatus.equals("6001")){ //取消支付
            res = -2;
        }
        else if (resultStatus.equals("8000")) {  //Payment fail
            res = 1;
        } else {  //Payment fail
            res = -1;
        }
        return res;
    }

    /**
     * 页面跳转工具类
     * 方法名:GotoActivityByBundle
     * 方法参数:firstActivity,cls,bundle
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static void GotoActivityByBundle(Activity firstActivity, Class cls, Bundle bundle){

        Intent intent = new Intent();
        intent.setClass(firstActivity,cls);
        if (bundle!=null){
            intent.putExtras(bundle);
        }

        firstActivity.startActivity(intent);

    }

    /**
     * 微信参数设置类
     * 方法名:checkWeCartResult
     * 方法参数: appOrderInfo
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static PayReq checkWeCartResult(String appOrderInfo) {
        JSONObject orderInfo = null;
        PayReq request = new PayReq();
        try {
            orderInfo = new JSONObject(appOrderInfo);
            request.appId = orderInfo.getString("appId");
            request.partnerId = orderInfo.getString("mchId");
            request.prepayId = orderInfo.getString("prepayId");
            request.packageValue = orderInfo.getString("packageVal");
            request.nonceStr = orderInfo.getString("nonceStr");
            request.timeStamp = orderInfo.getString("timeStamp");
            request.sign = orderInfo.getString("paySign");

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return request;
    }

    /**
     * 实现：格式化微信返回状态码
     * 方法名:returnWeCartResult
     * 方法参数:resultStatus
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static int returnWeCartResult(int resultStatus) {
        int res = -1;
        if (resultStatus == -1) {  //Payment fail
            res = -1; // 转换为JSON格式用于返回
        } else if (resultStatus == -2) {  //Payment 取消
            res = -2;
        } else if (resultStatus == 0) {  //Payment success
            res = 0;
        } else {  //Payment fail
            res = -1;
        }
        return res;
    }

    /**
     * 实现;根据key值获取订单信息中的数据
     * 方法名:getparams
     * 方法参数:paramsKey，appOrderInfo，payType
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static String getparams(String paramsKey,String appOrderInfo,String payType){

        String params = "";

        try {
            if (payType.equals(LePayConfigure.ALIPAY)){

                return params;

            }else if (payType.equals(LePayConfigure.WECARTPAY)){

                return params;

            } else if (payType.equals(LePayConfigure.QUICKPAY)){

                if (appOrderInfo!=null&&!appOrderInfo.equals("null")){
                    JSONObject json = new JSONObject(appOrderInfo);

                    if (json.has(paramsKey)){
                        params = json.getString(paramsKey);
                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
            return params;
        }

        return params;
    }

    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_lepay_payment_progrees, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        spaceshipImage.setBackgroundResource(R.drawable.lepay_anim_list);
        AnimationDrawable animationDrawable = (AnimationDrawable) spaceshipImage.getBackground();
        //是否仅仅启动一次？


        animationDrawable.setOneShot(false);

        if(animationDrawable.isRunning())//是否正在运行？

        {
            animationDrawable.stop();//停止

        }
        animationDrawable.start();//启动

        Dialog loadingDialog = new Dialog(context, R.style.Lepay_loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }

    /**
     * 日期格式化
     * 方法名:getFormatDate
     * 方法参数:dateString
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */
    private static String getFormatDate(String dateString) {
        String dateStr = null;

        Date date;
        try {
            if (dateString!=null&&!dateString.equals("null")){

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
                date = (Date) sdf1.parse(dateString);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                dateStr = sdf2.format(date);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateStr;

    }


    /**
     * 实现验证手机号
     * 方法名:isMobileNO
     * 方法参数:mobiles
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */
    public static boolean isMobileNO(String mobiles){

        Pattern p = Pattern.compile("^[1]([3][0-9]{1}|[4][0-9]{1}|[5][0-9]{1}|[7][0-9]{1}|[8][0-9]{1})[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 实现：验证身份证号码
     * 方法名:isIdNO
     * 方法参数:Id
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static boolean isIdNO(String Id){
        Pattern p = Pattern.compile("(\\d{14}[0-9xX])|(\\d{17}[0-9xX])");
        Matcher m = p.matcher(Id);
        return m.matches();
    }

    /**
     * 实现;验证姓名
     * 方法名:isCN
     * 方法参数:name
     * 作者: Thomson King(ouqikang@unionpay95516.com)
     * FIXME
     */

    public static boolean isCN(String name){
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5]+$");
        Matcher m = p.matcher(name);
        return m.matches();

    }






}