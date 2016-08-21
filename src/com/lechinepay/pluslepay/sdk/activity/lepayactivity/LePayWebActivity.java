package com.lechinepay.pluslepay.sdk.activity.lepayactivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lechinepay.pluslepay.R;
import com.lechinepay.pluslepay.manger.LePayActivityManager;


public class LePayWebActivity extends LePayActivityManager {

    /**
     * 标题
     */
    public static final String INTENT_KEY_TITLE = "INTENT_KEY_TITLE";
    /**
     * Web URL
     */
    public static final String INTENT_KEY_URL = "INTENT_KEY_URL";
    /**
     * 是否延迟加载图片
     */
    public static final String INTENT_KEY_IMG_DELAY = "INTENT_KEY_IMG_DELAY";
    public static final String HTTP_URL = "http://";

    private ProgressBar pgsBar;
    private WebView webView;

    private String mTitle;
    private String mUrl;
    private boolean mIsImgDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_pay_web);
        initView();
        stupData();
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(INTENT_KEY_URL);
        if (mUrl == null || mUrl.length() == 0 || !mUrl.startsWith(HTTP_URL)) { // 非法URL 退出
            Toast.makeText(this, "请求URL不合法", Toast.LENGTH_LONG).show();
            finish();
        }
        mTitle = bundle.getString(INTENT_KEY_TITLE);
        mIsImgDelay = bundle.getBoolean(INTENT_KEY_IMG_DELAY, true);
        if (mTitle == null) mTitle = "标题";
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webView);
        pgsBar = (ProgressBar) findViewById(R.id.pgsBar);

        initWebSetting();
        initWebView();
    }

    private void stupData() {
        webView.loadUrl(mUrl);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //设置支持Javascript
//		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //设置 缓存模式
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
//		webSettings.setDatabaseEnabled(true); // 开启 database storage API 功能
//		webSettings.setAppCacheEnabled(true); // 开启Application H5 Caches 功能
//		webSettings.setAllowFileAccess(true);
//		webSettings.setBlockNetworkImage(true);

//		webSettings.setSavePassword(true);
//		webSettings.setSupportZoom(true);
//		webSettings.setBuiltInZoomControls(true); //页面添加缩放按钮
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		webSettings.setUseWideViewPort(true);
//
//		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); //取消滚动条
//		webView.setHorizontalScrollbarOverlay(true);
//		webView.setHorizontalScrollBarEnabled(true);
//		webView.requestFocus();

        // 图片延迟加载
        if (mIsImgDelay) webSettings.setBlockNetworkImage(true);
//		if(Build.VERSION.SDK_INT >= 19) {
//			webSettings.setLoadsImagesAutomatically(true);
//		} else {
//			webSettings.setLoadsImagesAutomatically(false);
//		}
    }

    protected void initWebView() {
        setWebChromeClient(new MyWebChromeClient());
        setWebViewClient(new MyWebViewClient());
    }

    protected void setWebChromeClient(WebChromeClient client) {
        webView.setWebChromeClient(client);
    }

    protected void setWebViewClient(WebViewClient client) {
        webView.setWebViewClient(client);
    }

    /**
     * 页面返回
     */
    protected void doWebBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    /**
     * KeyBoard Back
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doWebBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_back:
//                doWebBack();
//                break;
//            default:
//                break;
//        }
//    }

    class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            pgsBar.setProgress(progress); // 进度设置
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pgsBar.setVisibility(View.VISIBLE); // 进度显示
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pgsBar.setVisibility(View.GONE); // 进度隐藏

            // 图片延长加载
            if (mIsImgDelay) webView.getSettings().setBlockNetworkImage(false);
//			if(mIsImgDelay && !webView.getSettings().getLoadsImagesAutomatically()) {
//				webView.getSettings().setLoadsImagesAutomatically(true);
//			}

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            pgsBar.setVisibility(View.GONE); // 进度隐藏
            Toast.makeText(LePayWebActivity.this, "", Toast.LENGTH_SHORT).show();
        }

//		@Override
//		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////			处理https请求，为WebView处理ssl证书设置
////			handler.proceed();  // 接受信任所有网站的证书
////			handler.cancel();   // 默认操作 不处理
////			handler.handleMessage(null);  // 可做其他处理
//		}
    }
}
