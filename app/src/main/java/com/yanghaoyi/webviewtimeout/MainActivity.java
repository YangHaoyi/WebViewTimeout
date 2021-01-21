package com.yanghaoyi.webviewtimeout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.yanghaoyi.webviewtimeout.constants.ManualConstants;
import com.yanghaoyi.webviewtimeout.presenter.TimeOutPresenter;
import com.yanghaoyi.webviewtimeout.presenter.util.ManualLogUtil;
import com.yanghaoyi.webviewtimeout.view.IManualView;
import com.yanghaoyi.webviewtimeout.view.impl.ManualViewImpl;

import static com.yanghaoyi.webviewtimeout.constants.ManualConstants.DEFAULT_CODE;

/**
 * @author : YangHaoYi on 2021/1/21.
 * Email  :  yang.haoyi@qq.com
 * Description : WebView超时Demo
 * Change : YangHaoYi on 2021/1/21.
 * Version : V 1.0
 */
public class MainActivity extends FragmentActivity {

    private WebView webView;
    private FrameLayout fmNetError;
    private int webError = DEFAULT_CODE;
    private TimeOutPresenter timeOutPresenter;
    private IManualView manualView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /** 初始化 **/
    private void init(){
        initView();
        initEvent();
    }

    /** 初始化View **/
    private void initView(){
        webView = findViewById(R.id.webView);
        fmNetError = findViewById(R.id.fmNetError);
        manualView = new ManualViewImpl(this,webView,fmNetError);
    }

    /** 初始化事件 **/
    private void initEvent(){
        initWebSettings();
        timeOutPresenter = new TimeOutPresenter(manualView);
        timeOutPresenter.startTimer();
        webView.loadUrl(ManualConstants.WEB_URL);
        setWebClient();
    }

    /** 初始化WebView设置 **/
    private void initWebSettings(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true); //保存数据
        webSettings.setJavaScriptEnabled(true); //启用js
        webSettings.setBlockNetworkImage(false); //解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setUserAgentString("User-Agent:Android");
    }

    /** 设置WebView Client **/
    private void setWebClient(){
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                ManualLogUtil.logMessage("---onPageStarted---");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webError == DEFAULT_CODE) {
                    manualView.showLoadingView();
                    //页面加载完成，停止并销毁计时器
                    timeOutPresenter.stopTimer();
                }
                ManualLogUtil.logMessage("---onPageFinished---");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); //接受所有证书验证
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ManualLogUtil.logMessage("onReceivedError: error = " + error);
                ManualLogUtil.logMessage("onReceivedError: " + error.getDescription() +";" + error.getErrorCode());
                if(error.getErrorCode()!=ManualConstants.CODE_TIME_OUT){
                    manualView.showNetErrorView();
                }else {
                    manualView.showLoadingView();
                }
            }
        });
    }

}