//package com.docker.core.widget.refresh.util;
//
//import android.databinding.BaseObservable;
//import android.databinding.Bindable;
//
//import com.scwang.smartrefresh.layout.BR;
//
//import java.io.Serializable;
//
///*
//*
//* smartrfreshlayout 对应的的状态操作类
//*
//* */
//public class SmartRefreshVo extends BaseObservable implements Serializable {
//
//
//    /*
//    *  关闭loading 和 加载更多  true(正常关闭) false(加载失败的时候关闭)
//    * */
//    public boolean complete;
//    @Bindable
//    public boolean getComplete() {
//        return complete;
//    }
//    public void setComplete(boolean complete) {
//        this.complete = complete;
//        notifyPropertyChanged(BR.complete);
//    }
//
//
//    /*
//    * 是否启用刷新 默认开启
//    * */
//    public boolean enableRefresh = true;
//
//    @Bindable
//    public boolean isEnableRefresh() {
//        return enableRefresh;
//    }
//
//    public void setEnableRefresh(boolean enableRefresh) {
//        this.enableRefresh = enableRefresh;
//        notifyPropertyChanged(BR.enableRefresh);
//    }
//
//
//
//
//
//
//}
