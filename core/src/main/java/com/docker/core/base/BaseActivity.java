package com.docker.core.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.docker.core.R;
import com.docker.core.util.Empty;
import com.docker.core.widget.ToolBar;
import com.gyf.barlibrary.ImmersionBar;

import javax.inject.Inject;


public abstract class BaseActivity<VM extends BaseViewModel, VB extends ViewDataBinding> extends BaseInjectActivity {


    protected VB mBinding;
    protected VM mViewModel;
    protected ToolBar mToolbar;
    private int mThemeColor = -1;

    protected abstract int getLayoutId();

    public abstract VM getViewModel();

    @Inject
    Empty empty;
    private InputMethodManager mInputMethodManager;

    /*
     *  是否要覆盖父布局
     * */
    public boolean isOverrideContentView = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        if (isOverrideContentView) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        } else {
            if (this.mThemeColor == -1) {
                TypedValue typedValue = new TypedValue();
                this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
                this.mThemeColor = typedValue.data;
            }
            setContentView(R.layout.activity_base);
            LinearLayout rootView = (LinearLayout) findViewById(R.id.root_layout);
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), rootView, false);
            initToolBar(rootView);
            if (this.mBinding != null) {
                rootView.addView(this.mBinding.getRoot(), new ViewGroup.LayoutParams(-1, -1));
            } else {
                rootView.addView(this.getLayoutInflater().inflate(this.getLayoutId(), (ViewGroup) null));
            }
        }
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        mViewModel = getViewModel();
        getLifecycle().addObserver(mViewModel);
    }

    /*
     *
     * 初始化状态栏  默认是白色黑字
     * */
    protected void initImmersionBar() {
        ImmersionBar.with(this).init();
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff")
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
                .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
                .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
                .flymeOSStatusBarFontColor("#000000")  //修改flyme OS状态栏字体颜色
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy();
        }
    }

    @Override
    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
            // 非必加
            // 如果你的app可以横竖屏切换，适配了4.4或者华为emui3.1系统手机，并且navigationBarWithKitkatEnable为true，
            // 请务必在onConfigurationChanged方法里添加如下代码（同时满足这三个条件才需要加上代码哦：1、横竖屏可以切换；2、android4.4或者华为emui3.1系统手机；3、navigationBarWithKitkatEnable为true）
            // 否则请忽略
            ImmersionBar.with(this).init();
        }
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.mInputMethodManager != null)) {
            this.mInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    protected View getToolBar() {
        return this.getLayoutInflater().inflate(R.layout.toolbar, (ViewGroup) null);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public ToolBar getToolbar() {
        return this.mToolbar;
    }

    /*
     *
     * 未覆盖父布局的默认包含一个toolbar
     * */
    protected void initToolBar(ViewGroup rootView) {
        View toolBar = this.getToolBar();
        if (toolBar != null) {
            rootView.addView(toolBar);
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            if (toolbar != null) {
                this.setSupportActionBar(toolbar);
            }

            this.mToolbar = new ToolBar(toolbar, this.getSupportActionBar(), this.mThemeColor);
            this.mToolbar.setTitle(String.valueOf(this.getTitle()));
            toolbar.setNavigationOnClickListener((v) -> {
                this.finish();
            });
        }

    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);

    }




}
