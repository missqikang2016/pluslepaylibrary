package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.manger.LePayActivityManager;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.view.DataPickerDialogView;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.lechinepay.pluslepaytoolsdk.controller.tools.LePayEnCodeUtil;

import java.util.Calendar;

public class LePayPaymentCardAddActivity extends LePayActivityManager {

    private Button lepayBT_addCard_submitButton;

    private TextView lepayTV_bankCard_name;

    private TextView lepayTV_bankCard_cardNumber;

    private EditText lepayET_addCard_Name;

    private EditText lepayET_addCard_IdCard;

    private TextView lepayET_addCard_Validity;

    private EditText lepayET_addCard_BackNumber;

    private EditText lepayET_addCard_Phone;

    private LePayOrderInfoEntity payInfo;

    private String DATA_YEAR = "";

    private String DATA_MONTH = "";

    private ImageView lepayIV_BackImg;

    private static Dialog dialog;

    private TextView lepayTV_AddCard_tip;

    private ImageView lepayIV_AddCard_checkbox;

    private boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_le_pay_payment_card_add);

        initData();

        stupView();


    }

    private void initData() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            if (bundle.containsKey("payInfo")) {
                payInfo = (LePayOrderInfoEntity) bundle.getSerializable("payInfo");
            }

        }

        dialog = LePayTools.createLoadingDialog(LePayConfigure.LEPAY_CONTEXT, "");

    }

    private void stupView() {

        lepayTV_AddCard_tip = (TextView) findViewById(R.id.lepayTV_AddCard_tip);

        lepayTV_AddCard_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(LePayWebActivity.INTENT_KEY_TITLE, "");
                bundle.putString(LePayWebActivity.INTENT_KEY_URL, "http://lechinepay.com/agreenment/kuaijie.html");
                LePayTools.gotoActivityByBundle(LePayPaymentCardAddActivity.this, LePayWebActivity.class, bundle);

            }
        });

        lepayIV_AddCard_checkbox = (ImageView) findViewById(R.id.lepayIV_AddCard_checkbox);

        lepayIV_AddCard_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isChecked) {

                    isChecked = false;

                    lepayIV_AddCard_checkbox.setImageResource(R.drawable.radio_n);


                } else {

                    isChecked = true;

                    lepayIV_AddCard_checkbox.setImageResource(R.drawable.radio_s);

                }

            }
        });

        lepayIV_BackImg = (ImageView) findViewById(R.id.lepayIV_BackImg);

        lepayIV_BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepayTV_bankCard_name = (TextView) findViewById(R.id.lepayTV_bankCard_name);

        lepayTV_bankCard_name.setText(payInfo.getBankCardName());

        lepayTV_bankCard_cardNumber = (TextView) findViewById(R.id.lepayTV_bankCard_cardNumber);

        lepayTV_bankCard_cardNumber.setText(payInfo.getBankCardNo());

        payInfo.setBankCardNo(LePayEnCodeUtil.encrypt(payInfo.getBankCardNo(), Environment.getExternalStorageDirectory() + "/LePayFile/config.cer"));

        lepayET_addCard_Name = (EditText) findViewById(R.id.lepayET_addCard_Name);

        lepayET_addCard_IdCard = (EditText) findViewById(R.id.lepayET_addCard_IdCard);

        lepayET_addCard_Validity = (TextView) findViewById(R.id.lepayET_addCard_Validity);

        lepayET_addCard_Validity.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();

            @Override
            public void onClick(View v) {

                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DataPickerDialogView(LePayPaymentCardAddActivity.this, 0, new DataPickerDialogView.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("%d-%d", startYear,
                                startMonthOfYear + 1);
                        DATA_YEAR = startYear + "";
                        DATA_MONTH = Integer.toString(startMonthOfYear + 1);
                        lepayET_addCard_Validity.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
            }


        });

        lepayET_addCard_BackNumber = (EditText) findViewById(R.id.lepayET_addCard_BackNumber);

        lepayET_addCard_Phone = (EditText) findViewById(R.id.lepayET_addCard_Phone);

        if (payInfo.getCardType() == 1) {

            lepayET_addCard_Validity.setVisibility(View.GONE);

            lepayET_addCard_BackNumber.setVisibility(View.GONE);

        }

        lepayBT_addCard_submitButton = (Button) findViewById(R.id.lepayBT_addCard_submitButton);

        lepayBT_addCard_submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (!LePayTools.isCN(lepayET_addCard_Name.getText().toString().trim())) {
                    Toast.makeText(LePayPaymentCardAddActivity.this, "姓名格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!LePayTools.isIdNO(lepayET_addCard_IdCard.getText().toString().trim())) {
                    Toast.makeText(LePayPaymentCardAddActivity.this, "身份证号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (payInfo.getCardType() != 1) {
                    if (DATA_YEAR.equals("") || DATA_MONTH.equals("")) {
                        Toast.makeText(LePayPaymentCardAddActivity.this, "请选择有效期", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (lepayET_addCard_BackNumber.getText().toString().trim().equals("")) {
                        Toast.makeText(LePayPaymentCardAddActivity.this, "请输入信用卡背面三位数字", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!LePayTools.isMobileNO(lepayET_addCard_Phone.getText().toString().trim())) {
                    Toast.makeText(LePayPaymentCardAddActivity.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isChecked) {
                    Toast.makeText(LePayPaymentCardAddActivity.this, "请先阅读并同意畅捷支付服务协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                payInfo.setIdName(lepayET_addCard_Name.getText().toString().trim());
                payInfo.setIdCardNo(LePayEnCodeUtil.encrypt(lepayET_addCard_IdCard.getText().toString().trim(), Environment.getExternalStorageDirectory() + "/LePayFile/config.cer"));

                payInfo.setMobile(lepayET_addCard_Phone.getText().toString().trim());
                payInfo.setCardYear(DATA_YEAR);
                payInfo.setCardMonth(DATA_MONTH);
                payInfo.setCvNum(lepayET_addCard_BackNumber.getText().toString().trim());

                Bundle bundle = new Bundle();
                bundle.putSerializable("payInfo", payInfo);
                LePayTools.gotoActivityByBundle(LePayPaymentCardAddActivity.this, LePayPaymentGetCodeActivity.class, bundle);
            }
        });

    }

//    private class AddQuickBankCardAnsycTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            if (dialog!=null&&!dialog.isShowing()){
//
//                dialog.show();
//
//            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            String result = null;
//
//            LePayControllerManage lePayControllerManage = new LePayControllerManage();
//
//            try {
//                try {
//
//                    result = lePayControllerManage.addBankCard(LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, payInfo.getBuyerId(),payInfo.getBankCardNo(), payInfo.getMobile(), LePayConfigure.LEPAY_MCHID, payInfo.getIdName(), payInfo.getIdCardNo(), payInfo.getCardYear(), payInfo.getCardMonth(), payInfo.getCvNum(), payInfo.getCompanyPersonal(), payInfo.getInstId(), payInfo.getInstCode(), payInfo.getBankCardName(), payInfo.getDbcr());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//
//            return result;
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            lepayBT_addCard_submitButton.setEnabled(true);
//            if (dialog!=null&&dialog.isShowing())
//                dialog.cancel();
//
//            if (result!=null){
//                LePayAddBankCardResultBean lePayAddBankCardResultBean = LePayJsonFuncation.AddBankCard(result);
//                if (lePayAddBankCardResultBean != null) {
//
//                    if (lePayAddBankCardResultBean.getStatus() == 1) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("payInfo", payInfo);
//                        LePayTools.gotoActivityByBundle(LePayPaymentCardAddActivity.this, LePayPaymentGetCodeActivity.class, bundle);
//                        destoryActivity();
//
//                    } else {
//
//                        Toast.makeText(LePayPaymentCardAddActivity.this, lePayAddBankCardResultBean.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                }
//            }else{
//
//                Toast.makeText(LePayPaymentCardAddActivity.this, "参数错误或系统连接失败", Toast.LENGTH_LONG).show();
//
//            }
//
//
//
//        }
//    }

}
