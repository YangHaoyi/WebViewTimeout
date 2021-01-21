package com.yanghaoyi.webviewtimeout.presenter;



import com.yanghaoyi.webviewtimeout.presenter.util.ManualLogUtil;
import com.yanghaoyi.webviewtimeout.view.IManualView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : YangHaoYi on 2021/1/21.
 * Email  :  yang.haoyi@qq.com
 * Description :
 * Change : YangHaoYi on 2021/1/21.
 * Version : V 1.0
 */
public class TimeOutPresenter {

    /** 超时时间 **/
    private static final int TIME_PERIOD = 1000;
    /** 计时时间 **/
    private int currentTime;
    /** 主页面是否加载 **/
    private boolean isPageLoaded;
    /** 计时Timer **/
    private Timer timer;
    /** 计时器任务 **/
    private TimerTask timerTask;
    /** view接口 **/
    private IManualView manuView;

    public TimeOutPresenter(IManualView manuView) {
        this.manuView = manuView;
    }

    /** 开始计时器 **/
    public void startTimer(){
        isPageLoaded = false;
        if (null == timer) {
            timer = new Timer();
            timerTask = new TimeOutTimerTask();
            timer.schedule(timerTask,0, TIME_PERIOD);
        }
    }

    /** 停止计时器 **/
    public void stopTimer(){
        isPageLoaded = true;
        if(null!=timer){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if(null!=timerTask){
            timerTask.cancel();
            timerTask = null;
        }
    }

    /** 处理页面超时事件 **/
    private void dealWithTimeout(){
        ManualLogUtil.logMessage("dealWithTimeout isPageLoaded is : " + isPageLoaded);
        if(!isPageLoaded){
            ManualLogUtil.logMessage("showNetErrorView");
            manuView.showNetErrorView();
        }
        stopTimer();
    }

    /** 设置页面是否加载完成 **/
    public void setPageLoaded(boolean pageLoaded) {
        isPageLoaded = pageLoaded;
    }

    /** 计时任务 **/
    class TimeOutTimerTask extends TimerTask {
        @Override
        public void run() {
            currentTime++;
            ManualLogUtil.logMessage("currentTime is : " + currentTime);
            if (currentTime > 9) {
                dealWithTimeout();
            }
        }
    }
}
