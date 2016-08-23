package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.manger.LePayActivityManager;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayQuickPayResultBean;
import com.lechinepay.pluslepay.tools.LePayTools;


public class LePayPaymentResultActivity extends LePayActivityManager {

    private Button lepayBT_submitButton;

    private TextView lepayTV_commodityName, lepayTV_transactionNumber, lepayTV_closingTime, lepayTV_paymentMethod, lepayTV_amount, lepay_result_title;

    private String respCode;

    private String respMsg;

    private LePayOrderInfoEntity payInfo;

    private LePayQuickPayResultBean payResult = null;

    private TextView lepayTV_paymentStatus;

    private LinearLayout lepayLV_Result_Status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        initData();
        if (payInfo != null) {

            initView();
        }

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.containsKey("payInfo")) {
                payInfo = (LePayOrderInfoEntity) bundle.getSerializable("payInfo");
            }
            if (bundle.containsKey("pay_result")) {
                payResult = (LePayQuickPayResultBean) bundle.getSerializable("pay_result");
            }

        }

    }

    private void initView() {

        lepayLV_Result_Status = (LinearLayout) findViewById(R.id.lepayLV_Result_Status);

        lepayTV_paymentStatus = (TextView) findViewById(R.id.lepayTV_paymentStatus);

        lepayTV_commodityName = (TextView) findViewById(R.id.lepayTV_commodityName);

        lepayTV_commodityName.setText(payInfo.getSummary());

        lepayTV_transactionNumber = (TextView) findViewById(R.id.lepayTV_transactionNumber);

        lepayTV_transactionNumber.setText(payInfo.getTradeNo());

        lepayTV_closingTime = (TextView) findViewById(R.id.lepayTV_closingTime);

        lepayTV_closingTime.setText(payInfo.getStartTime());

        lepayTV_paymentMethod = (TextView) findViewById(R.id.lepayTV_paymentMethod);

        if (payInfo.getPayChnlType().equals(LePayConfigure.ALIPAY)){

            lepayTV_paymentMethod.setText("支付宝");

        }else if(payInfo.getPayChnlType().equals(LePayConfigure.WECARTPAY)){

            lepayTV_paymentMethod.setText("微信");

        }else if(payInfo.getPayChnlType().equals(LePayConfigure.QUICKPAY)){

            lepayTV_paymentMethod.setText("快捷支付");

        }


        lepayTV_amount = (TextView) findViewById(R.id.lepayTV_amount);

        String amount_float = Float.parseFloat(payInfo.getAmount()) / 100 + "";

        lepayTV_amount.setText(amount_float);

        lepayBT_submitButton = (Button) findViewById(R.id.lepayBT_submitButton);

        lepayBT_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepay_result_title = (TextView) findViewById(R.id.lepay_result_title);

        if (payInfo.isSuccess()) {

            lepay_result_title.setText("支付成功");

        } else {

            lepay_result_title.setText("支付失败");

        }

        if (payResult != null) {

            lepayLV_Result_Status.setVisibility(View.VISIBLE);

            String status = "";

            if (payResult.getTranStatus() !=null && payResult.getTranStatus().equals("S")){

                status = "支付成功";

            }else{

                status = "支付处理中";

            }
            lepayTV_paymentStatus.setText(status);

            if (payResult.getRespCode().equals("000000")) {

                lepay_result_title.setText("交易成功");
                LePayConfigure.CHANNELSPAYLISTENNER.OnChannelsPayEnd(LePayTools.setResultInfo("0",payInfo.getPayChnlType(), 0, status));

            } else {
                lepayTV_paymentStatus.setText("支付失败");
                lepayTV_paymentStatus.setTextColor(Color.RED);
                lepay_result_title.setText("交易失败");
                lepayTV_amount.setTextColor(Color.RED);
                LePayConfigure.CHANNELSPAYLISTENNER.OnChannelsPayEnd(LePayTools.setResultInfo("-1",payInfo.getPayChnlType(), -1, payResult.getRespMsg()));

            }
        }


    }

}
