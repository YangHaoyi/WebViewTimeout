package com.yanghaoyi.webviewtimeout.view.impl;

import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.yanghaoyi.webviewtimeout.view.IManualView;

/**
 * @author : YangHaoYi on 2021/1/21.
 * Email  :  yang.haoyi@qq.com
 * Description :
 * Change : YangHaoYi on 2021/1/21.
 * Version : V 1.0
 */
public class ManualViewImpl implements IManualView {

    private FragmentActivity activity;
    private WebView webView;
    private FrameLayout netErrorView;

    public ManualViewImpl(FragmentActivity activity,WebView webView, FrameLayout netErrorView) {
        this.activity = activity;
        this.webView = webView;
        this.netErrorView = netErrorView;
    }

    @Override
    public void showLoadingView() {
        webView.setVisibility(View.VISIBLE);
        netErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showNetErrorView() {
        //收到超时事件，显示安卓端超时视图
        activity.runOnUiThread(new Runnable() {
            public void run() {
                webView.setVisibility(View.GONE);
                netErrorView.setVisibility(View.VISIBLE);
            }
        });
    }
}
