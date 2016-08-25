package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.configure.LePayConfigure;
import com.lechinepay.pluslepay.manger.LePayActivityManager;
import com.lechinepay.pluslepay.sdk.Entity.LePayOrderInfoEntity;
import com.lechinepay.pluslepay.sdk.json.LePayJsonFuncation;
import com.lechinepay.pluslepay.sdk.json.resultentity.LePayBankCardInfoBean;
import com.lechinepay.pluslepay.tools.LePayTools;
import com.lechinepay.pluslepaytoolsdk.controller.LePayController;
import com.lechinepay.pluslepaytoolsdk.controller.LePayControllerManage;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LePayPayMentCardListActivity extends LePayActivityManager {

    private ListView lepayLTV_bankCardListView;
    private Button lepayBT_addCard_submitButton;
    private List<LePayBankCardInfoBean> bankCardslist;

    private LePayOrderInfoEntity payInfo;

    private ImageView lepayIV_BackImg;

    private Dialog dialog;

    private View DELETE_VIEW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_pay_pay_ment_card_list);
        initData();
        if (payInfo != null) {
            stupview();
        }
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("payInfo"))
                payInfo = (LePayOrderInfoEntity) bundle.getSerializable("payInfo");
        }
    }

    private void stupview() {

        dialog = LePayTools.createLoadingDialog(LePayPayMentCardListActivity.this,"");

        lepayIV_BackImg = (ImageView) findViewById(R.id.lepayIV_BackImg);

        lepayIV_BackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lepayLTV_bankCardListView = (ListView) findViewById(R.id.lepayLTV_bankCardListView);

        lepayBT_addCard_submitButton = (Button) findViewById(R.id.lepayBT_addCard_submitButton);

        bankCardslist = new ArrayList<LePayBankCardInfoBean>();

        new InitInfoAsyncTask().execute();

        lepayBT_addCard_submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                addCardService();

            }

        });

    }

    private void addCardService() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("payInfo", payInfo);
        LePayTools.gotoActivityByBundle(LePayPayMentCardListActivity.this, LePayPaymentInputCardNumberActivity.class, bundle);

    }


    private class InitInfoAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (dialog!=null&&!dialog.isShowing())
                dialog.show();

        }


        @Override
        protected String doInBackground(String... params) {

            LePayControllerManage lePayControllerManage = new LePayControllerManage();

            String result = null;
            try {

                result = lePayControllerManage.queryQuickPaymentBankCards(LePayConfigure.LEPAY_MCHID, LePayConfigure.LEPAY_CMPAPPID, payInfo.getBuyerId());
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

                bankCardslist = LePayJsonFuncation.QureyCardsInfo(result);



                if (!bankCardslist.isEmpty()) {

                    setListView();

                } else {
                    setListView();
                    addCardService();

                }


            }
        }
    }


    private void setListView() {

        CommonAdapter<LePayBankCardInfoBean> commonAdapter = new CommonAdapter<LePayBankCardInfoBean>(this, R.layout.activity_lepay_bankcard_item, bankCardslist) {
            @Override
            protected void convert(final ViewHolder viewHolder, final LePayBankCardInfoBean item, final int position) {

                int a;
                a= position;
                if(a%2==0 )
                {
                    viewHolder.setBackgroundRes(R.id.lepayRV_AddCard_CardInfo,R.drawable.lepay_list_bg_green);
                }
                else {
                    viewHolder.setBackgroundRes(R.id.lepayRV_AddCard_CardInfo,R.drawable.lepay_list_bg_blue);
                }

                viewHolder.setText(R.id.lepayTV_bankCard_cardNumber, item.getBankCardNumber());

                if (item.getBankCardName()!=null){

                    viewHolder.setText(R.id.lepayTV_bankCard_name, item.getBankCardName());

                }else{

                    viewHolder.setText(R.id.lepayTV_bankCard_name, "");

                }

                if (item.getBankCardType().equals("DC")){

                    viewHolder.setText(R.id.lepayTV_bankCard_type, "借记卡");

                }else if(item.getBankCardType().equals("CC")){

                    viewHolder.setText(R.id.lepayTV_bankCard_type, "贷记卡");

                }else{

                    viewHolder.setText(R.id.lepayTV_bankCard_type, "");

                }

                viewHolder.setOnClickListener(R.id.lepayRV_AddCard_CardInfo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        payInfo.setMobile(item.getPhoneNumber());

                        payInfo.setBindId(item.getBindId());

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("payInfo", payInfo);
                        LePayTools.gotoActivityByBundle(LePayPayMentCardListActivity.this, LePayPaymentGetCodeActivity.class, bundle);

                    }
                });

                viewHolder.setOnLongClickListener(R.id.lepayRV_AddCard_CardInfo, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        viewHolder.setVisible(R.id.lepayRV_bankCard_item_deleteView, true);

                        return true;
                    }
                });

                viewHolder.setOnClickListener(R.id.lepayRV_bankCard_item_deleteView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        v.setVisibility(View.GONE);

                    }
                });

                viewHolder.setOnClickListener(R.id.lepayTV_bankCard_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DELETE_VIEW = v;
                        DELETE_VIEW.setEnabled(false);

                        deleteBankCard(item.getBindId());

                    }
                });

                viewHolder.setOnClickListener(R.id.lepayTV_bankCard_cancle, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        viewHolder.setVisible(R.id.lepayRV_bankCard_item_deleteView, false);

                    }
                });

            }
        };

        lepayLTV_bankCardListView.setAdapter(commonAdapter);

        commonAdapter.notifyDataSetChanged();

        setListViewHeightBasedOnChildren(lepayLTV_bankCardListView);


    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//if without this statement,the listview will be a little short
        listView.setLayoutParams(params);
    }

    private void deleteBankCard(String bindId) {


        new DeleteBankCardAnsycTask().execute(bindId);


    }

    private class DeleteBankCardAnsycTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String bindId = params[0];

            String result = null;

            LePayController lePayControllerManage = new LePayControllerManage();
            try {

                result = lePayControllerManage.deleteQuickPaymentCard(bindId);
            }catch (Exception e){
                e.printStackTrace();
            }



            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            DELETE_VIEW.setEnabled(true);

            if (result != null) {

                stupview();

            }


        }
    }


}
