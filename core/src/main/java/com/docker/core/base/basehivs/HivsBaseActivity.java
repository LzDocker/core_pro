package com.docker.core.base.basehivs;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.docker.core.base.BaseActivity;
import com.docker.core.util.Empty;
import com.docker.core.util.ViewEventResouce;
import com.docker.core.widget.dialog.WaitDialog;
import com.gyf.barlibrary.ImmersionBar;

import javax.inject.Inject;

public abstract class HivsBaseActivity<VM extends HivsBaseViewModel, VB extends ViewDataBinding> extends BaseActivity<VM, VB> {

    public WaitDialog waitDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        initBasicListener();
        initView();
    }

    @Inject
    Empty empty;

    public void initBasicListener() {
        mViewModel.mResourceLiveData.observe(this, o -> {
        });
        mViewModel.mVmEventSouce.observe(this, viewEventResouce -> {
            switch (viewEventResouce.eventType) {
                case 101:
                    showWaitDialog(viewEventResouce.message, (Boolean) viewEventResouce.data);
                    break;
                case 102:
                    hidWaitDialog();
                    break;
            }
            OnVmEnentListner(viewEventResouce);
        });
    }

    public void OnVmEnentListner(ViewEventResouce viewEventResouce) {

    }

    public void showWaitDialog(String message, boolean cancleable) {
        if (waitDialog == null) {
            waitDialog = new WaitDialog(this);
        }
        waitDialog.show(this, message, cancleable, null);
    }

    public void hidWaitDialog() {
        if (waitDialog != null) {
//            waitDialog.hide();
            waitDialog.dismiss();
        }
    }


    @Override
    protected void initImmersionBar() {

        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .navigationBarColor("#ffffff")
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    public abstract void initView();


}
