package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.manger.LePayActivityManager;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.json.LePayJsonFuncation;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayQuickPayResultBean;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePaySendCodeResultBean;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.lechinepay.pluslepaytoolsdk.controller.LePayControllerManage;
import com.socks.library.KLog;

import java.io.IOException;

public class LePayPaymentGetCodeActivity extends LePayActivityManager {

    private LinearLayout lepayLV_getCode_Tip;

    private TextView lepayTV_getCode_Amount;

    private ImageView lepayIV_BackImg;

    private TextView lepayTV_getCode_Commodity;

    private TextView lepayTV_PhoneLast;

    private EditText lepayET_PhoneCode;

    private Button lepayBT_getCode_ReSend;

    private Button lepay_getCode_Submit;

    private LePayOrderInfoEntity payInfo;

    private String verifyCode;

    private String payTypeTradeNo;

    private TimeCount time;

    private Dialog dialog;

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            lepayBT_getCode_ReSend.setBackgroundResource(R.drawable.lepay_btn_gray);
            lepayBT_getCode_ReSend.setClickable(false);
            lepayBT_getCode_ReSend.setText("重新发送(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            lepayBT_getCode_ReSend.setText("发送验证码");
            lepayBT_getCode_ReSend.setClickable(true);
            lepayBT_getCode_ReSend.setBackgroundResource(R.drawable.lepay_btn_green);
            lepayLV_getCode_Tip.setVisibility(View.GONE);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_pay_payment_get_code);

        initData();

        if (payInfo != null) {

            stupView();
        }


    }

    private void stupView() {

        lepayIV_BackImg = (ImageView) findViewById(R.id.lepayIV_BackImg);

        lepayIV_BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepayTV_getCode_Amount = (TextView) findViewById(R.id.lepayTV_getCode_Amount);

        lepayLV_getCode_Tip = (LinearLayout) findViewById(R.id.lepayLV_getCode_Tip);

        float floatAmount = Float.parseFloat(payInfo.getAmount());

        String AMOUNT = floatAmount / 100 + "";

        lepayTV_getCode_Amount.setText(AMOUNT);

        lepayTV_getCode_Commodity = (TextView) findViewById(R.id.lepayTV_getCode_Commodity);

        lepayTV_getCode_Commodity.setText(payInfo.getSummary());

        lepayTV_PhoneLast = (TextView) findViewById(R.id.lepayTV_PhoneLast);

        lepayTV_PhoneLast.setText(payInfo.getMobile().substring(payInfo.getMobile().length() - 4, payInfo.getMobile().length()));

        lepayET_PhoneCode = (EditText) findViewById(R.id.lepayET_PhoneCode);

        lepayBT_getCode_ReSend = (Button) findViewById(R.id.lepayBT_getCode_ReSend);

        lepayBT_getCode_ReSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (payInfo.getBuyerId() != null) {

                    if (payInfo.getBindId()!=null){

                        new getPayPhoneCodeByBindIdAnsycTask().execute();

                    }else{

                        new getPayPhoneCodeAnsycTask().execute();

                    }



                } else {

                    new getPayPhoneCodeAnsycTask().execute();

                }

                time.start();

            }
        });

        lepay_getCode_Submit = (Button) findViewById(R.id.lepay_getCode_Submit);

        lepay_getCode_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyCode = lepayET_PhoneCode.getText().toString().trim();

                if (verifyCode == null || verifyCode.equals("")) {

                    Toast.makeText(LePayPaymentGetCodeActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();

                    return;
                }

                new SendPayAnsycTask().execute();

            }
        });

        getCode();


    }

    private void getCode(){
        if (payInfo.getBuyerId() != null) {

            time.start();

            if (payInfo.getBindId()!=null){

                new getPayPhoneCodeByBindIdAnsycTask().execute();

            }else{

                new getPayPhoneCodeAnsycTask().execute();

            }

        } else {
            time.start();

            new getPayPhoneCodeAnsycTask().execute();

        }
    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.containsKey("payInfo"))
                payInfo = (LePayOrderInfoEntity) bundle.getSerializable("payInfo");

        }

        time = new TimeCount(60000, 1000);

        dialog = LePayTools.createLoadingDialog(LePayPaymentGetCodeActivity.this,"");

    }

    private class SendPayAnsycTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog!=null&&!dialog.isShowing())
                dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;

            LePayControllerManage lePayControllerManage = new LePayControllerManage();

            if(payTypeTradeNo!=null){
                try {

                    result = lePayControllerManage.submitQuickPaymentOrder("1.0.0", "UTF-8", "", LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, payInfo.getTradeNo(), payInfo.getToken(), payInfo.getProductCode(), verifyCode, payTypeTradeNo);

                }catch (Exception e){

                    e.printStackTrace();

                }
            }




            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog!=null&&dialog.isShowing())
                dialog.cancel();

            if (result != null) {


                LePayQuickPayResultBean lePayQuickPayResultBean = LePayJsonFuncation.QuickPayResult(result);

                if (lePayQuickPayResultBean != null) {

                    if ( lePayQuickPayResultBean.getRespCode()!=null&& lePayQuickPayResultBean.getRespCode().equals("000000")){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("pay_result", lePayQuickPayResultBean);
                        bundle.putSerializable("payInfo", payInfo);
                        LePayTools.gotoActivityByBundle(LePayPaymentGetCodeActivity.this, LePayPaymentResultActivity.class, bundle);
                        destoryActivity();
                    }else{

                        Toast.makeText(LePayPaymentGetCodeActivity.this, lePayQuickPayResultBean.getRespMsg(), Toast.LENGTH_LONG).show();

                    }


                }else{

                    Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

                }
            }else{

                Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

            }


        }
    }


    private class getPayPhoneCodeAnsycTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog!=null&&!dialog.isShowing())
                dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            LePayControllerManage lePayControllerManage = new LePayControllerManage();

            String dbcr = payInfo.getDbcr();
            try {
                result = lePayControllerManage.getSMSVerificationCode("1.0.0", "UTF-8", "", LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, payInfo.getTradeNo(), payInfo.getToken(), payInfo.getProductCode(), payInfo.getBankCardNo(), payInfo.getIdName(), payInfo.getIdCardNo(), payInfo.getMobile(), payInfo.getCardYear(), payInfo.getCardMonth(), payInfo.getCvNum(),dbcr,payInfo.getBuyerId());

            }catch (Exception e){
                e.printStackTrace();
            }



            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog!=null&&dialog.isShowing())
                dialog.cancel();
            if (result != null) {
                LePaySendCodeResultBean lePaySendCodeResultBean = LePayJsonFuncation.SendCode(result);
                if (lePaySendCodeResultBean != null) {

                    if (lePaySendCodeResultBean.getRespCode().equals("000000")) {

                        Toast.makeText(LePayPaymentGetCodeActivity.this, "短信发送成功", Toast.LENGTH_LONG).show();

                        lepayLV_getCode_Tip.setVisibility(View.VISIBLE);

                        payTypeTradeNo = lePaySendCodeResultBean.getPayTypeTradeNo();

                    } else {
                        Toast.makeText(LePayPaymentGetCodeActivity.this, lePaySendCodeResultBean.getRespMsg(), Toast.LENGTH_LONG).show();
                        time.cancel();
                        lepayBT_getCode_ReSend.setText("发送验证码");
                        lepayBT_getCode_ReSend.setClickable(true);
                        lepayBT_getCode_ReSend.setBackgroundResource(R.drawable.lepay_btn_green);
                        lepayLV_getCode_Tip.setVisibility(View.GONE);
                    }

                }else{

                    Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

                }
            }else{

                Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

            }


        }
    }


    private class getPayPhoneCodeByBindIdAnsycTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog!=null&&!dialog.isShowing())
                dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            LePayControllerManage lePayControllerManage = new LePayControllerManage();

            try {
                result = lePayControllerManage.getSMSVerificationCodeByBindId("1.0.0", "UTF-8", "", LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, payInfo.getTradeNo(), payInfo.getToken(), payInfo.getProductCode(),payInfo.getBindId());

            }catch (Exception e){
                e.printStackTrace();
            }


            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog!=null&&dialog.isShowing())
                dialog.cancel();
            if (result != null) {
                LePaySendCodeResultBean lePaySendCodeResultBean = LePayJsonFuncation.SendCode(result);
                if (lePaySendCodeResultBean != null) {

                    if (lePaySendCodeResultBean.getRespCode().equals("000000")) {

                        Toast.makeText(LePayPaymentGetCodeActivity.this, "短信发送成功", Toast.LENGTH_LONG).show();

                        lepayLV_getCode_Tip.setVisibility(View.VISIBLE);

                        payTypeTradeNo = lePaySendCodeResultBean.getPayTypeTradeNo();

                    } else {
                        Toast.makeText(LePayPaymentGetCodeActivity.this, lePaySendCodeResultBean.getRespMsg(), Toast.LENGTH_LONG).show();
                        time.cancel();
                        lepayBT_getCode_ReSend.setText("发送验证码");
                        lepayBT_getCode_ReSend.setClickable(true);
                        lepayBT_getCode_ReSend.setBackgroundResource(R.drawable.lepay_btn_green);
                        lepayLV_getCode_Tip.setVisibility(View.GONE);

                    }

                }else{

                    Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

                }

            }else{

                Toast.makeText(LePayPaymentGetCodeActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();

            }


        }
    }
}
