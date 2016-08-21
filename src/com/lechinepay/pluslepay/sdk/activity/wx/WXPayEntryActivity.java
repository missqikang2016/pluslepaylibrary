package com.lechinepay.pluslepay.sdk.activity.wx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.sdk.listener.ChannelsPayListener;
import com.lechinepay.pluslepay.tools.LePayLogTool;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "LePay.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    private Button lepayBT_submitButton;

    private TextView lepayTV_commodityName, lepayTV_transactionNumber, lepayTV_closingTime, lepayTV_paymentMethod, lepayTV_amount, lepay_result_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);

        initView();

        stupAliPayResult();

        api = WXAPIFactory.createWXAPI(this, null);

        api.handleIntent(getIntent(), this);


    }

    private void stupAliPayResult() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            String alipayresult = mBundle.getString("aliPay_result");
            if (alipayresult != null) {
                try {
                    JSONObject json = new JSONObject(alipayresult);
                    if (json.getInt("respCode") != 0) {
                        lepay_result_title.setText("支付失败");
                        lepayTV_amount.setTextColor(Color.RED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private void initView() {


        lepayTV_commodityName = (TextView) findViewById(R.id.lepayTV_commodityName);

        lepayTV_commodityName.setText(LePayConfigure.payInfo.getSummary());

        lepayTV_transactionNumber = (TextView) findViewById(R.id.lepayTV_transactionNumber);

        lepayTV_transactionNumber.setText(LePayConfigure.payInfo.getTradeNo());

        lepayTV_closingTime = (TextView) findViewById(R.id.lepayTV_closingTime);

        lepayTV_closingTime.setText(LePayConfigure.payInfo.getStartTime());

        lepayTV_paymentMethod = (TextView) findViewById(R.id.lepayTV_paymentMethod);

        if (LePayConfigure.payInfo.getPayChnlType().equals(LePayConfigure.ALIPAY)){

            lepayTV_paymentMethod.setText("支付宝");

        }else if(LePayConfigure.payInfo.getPayChnlType().equals(LePayConfigure.WECARTPAY)){

            lepayTV_paymentMethod.setText("微信");

        }else if(LePayConfigure.payInfo.getPayChnlType().equals(LePayConfigure.QUICKPAY)){

            lepayTV_paymentMethod.setText("快捷支付");

        }

        lepayTV_amount = (TextView) findViewById(R.id.lepayTV_amount);

        String amount_float = Float.parseFloat(LePayConfigure.payInfo.getAmount()) / 100 + "";

        lepayTV_amount.setText(amount_float);

        lepayBT_submitButton = (Button) findViewById(R.id.lepayBT_submitButton);

        lepayBT_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepay_result_title = (TextView) findViewById(R.id.lepay_result_title);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            String respMsg = "";

            if (resp.errCode == 0) {
                lepay_result_title.setText("支付成功");
                respMsg = "支付成功";
            } else if(resp.errCode == -2){
                lepay_result_title.setText("支付取消");
                respMsg = "支付取消";
            } else {
                lepay_result_title.setText("支付失败");
                respMsg = "支付失败";
            }

            ChannelsPayListener channelsPayListener = LePayConfigure.CHANNELSPAYLISTENNER;

            String result = LePayTools.setResultInfo(Integer.toString(resp.errCode),LePayConfigure.payInfo.getPayChnlType(), LePayTools.returnWeCartResult(resp.errCode),respMsg);

            channelsPayListener.OnChannelsPayEnd(result);

        }
    }


}
