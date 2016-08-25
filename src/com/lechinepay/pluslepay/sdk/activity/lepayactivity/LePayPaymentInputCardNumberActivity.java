package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.manger.LePayActivityManager;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.json.LePayJsonFuncation;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayBankNameResultBean;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.lechinepay.pluslepaytoolsdk.controller.LePayControllerManage;
import com.lechinepay.pluslepaytoolsdk.controller.tools.LePayEnCodeUtil;
import com.socks.library.KLog;

import java.io.IOException;

public class LePayPaymentInputCardNumberActivity extends LePayActivityManager {

    private String BANKCARD_NUMBER = null;

    private EditText lepayET_addCard_Number;

    private Button lepayBT_addCard_submitButton;

    private ImageView lepayIV_BackImg;


    private LePayOrderInfoEntity payInfo;


    private int CARDTYPE = -1;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_pay_payment_input_card_number);
        initData();
        if (payInfo != null) {
            stupView();
        }
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            if (bundle.containsKey("payInfo"))
                payInfo = (LePayOrderInfoEntity) bundle.getSerializable("payInfo");
        }

        dialog = LePayTools.createLoadingDialog(LePayPaymentInputCardNumberActivity.this, "");

    }

    private void stupView() {

        lepayIV_BackImg = (ImageView) findViewById(R.id.lepayIV_BackImg);

        lepayIV_BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepayET_addCard_Number = (EditText) findViewById(R.id.lepayET_addCard_Number);

        lepayET_addCard_Number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 9) {

                    lepayBT_addCard_submitButton.setBackgroundResource(R.drawable.lepay_btn_next_seletor);
                    lepayBT_addCard_submitButton.setEnabled(true);

                } else {
                    lepayBT_addCard_submitButton.setBackgroundResource(R.drawable.lepay_next_disable);
                    lepayBT_addCard_submitButton.setEnabled(false);
                }

            }
        });

        lepayBT_addCard_submitButton = (Button) findViewById(R.id.lepayBT_addCard_submitButton);

        lepayBT_addCard_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BANKCARD_NUMBER = lepayET_addCard_Number.getText().toString().trim();

                new QueryCardAnsycTask().execute();

            }
        });

        lepayBT_addCard_submitButton.setEnabled(false);


    }

    private class QueryCardAnsycTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (dialog != null && !dialog.isShowing()) {

                dialog.show();

            }
        }

        @Override
        protected String doInBackground(String... params) {

            LePayControllerManage lePayController = new LePayControllerManage();

            String result = null;



            try {
                result = lePayController.queryBankName(LePayEnCodeUtil.encrypt(BANKCARD_NUMBER,Environment.getExternalStorageDirectory()+ "/LePayFile/config.cer"),payInfo.getProductCode(), LePayConfigure.LEPAY_CMPAPPID, LePayConfigure.LEPAY_MCHID);

            }catch (Exception e){

                e.printStackTrace();

            }



            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialog != null && dialog.isShowing())
                dialog.cancel();

            LePayBankNameResultBean lePayBankNameResultBean = LePayJsonFuncation.BankCardName(result);

            if (lePayBankNameResultBean != null) {

                if (lePayBankNameResultBean.getCode().equals("000000")) {

                    payInfo.setBankCardName(lePayBankNameResultBean.getResult().getPayChannelList().get(0).getInstName());

                    if (lePayBankNameResultBean.getResult().getPayChannelList().get(0).getDbcr().equals("DC")) {


                        CARDTYPE = 1;

                    } else {


                        CARDTYPE = 2;

                    }

                    Bundle bundle = new Bundle();

                    payInfo.setBankCardNo(BANKCARD_NUMBER);

                    payInfo.setCardType(CARDTYPE);

                    payInfo.setInstId(lePayBankNameResultBean.getResult().getPayChannelList().get(0).getInstId());

                    payInfo.setCompanyPersonal(lePayBankNameResultBean.getResult().getPayChannelList().get(0).getCompanyPersonal());

                    payInfo.setInstCode(lePayBankNameResultBean.getResult().getPayChannelList().get(0).getInstCode());

                    payInfo.setDbcr(lePayBankNameResultBean.getResult().getPayChannelList().get(0).getDbcr());

                    bundle.putSerializable("payInfo", payInfo);

                    LePayTools.gotoActivityByBundle(LePayPaymentInputCardNumberActivity.this, LePayPaymentCardAddActivity.class, bundle);


                } else {

                    Toast.makeText(LePayPaymentInputCardNumberActivity.this, "暂不支持此卡", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(LePayPaymentInputCardNumberActivity.this, "暂不支持此卡", Toast.LENGTH_LONG).show();
            }


        }
    }
}
