package com.docker.core.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.docker.core.R;
import com.docker.core.util.AutoClearedValue;

/**
 * Created by zhangxindang on 2018/8/31.
 */

public abstract class BaseFragment<VM extends BaseViewModel, VB extends ViewDataBinding> extends BaseInjectFragment {

    public AutoClearedValue<VB> mBinding;
    public VM mViewModel;
    private LinearLayout rootView;

    public BaseFragment() {
    }

    protected abstract int getLayoutId();

    protected abstract VM getViewModel();

    protected abstract void initView(View var1);

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = (LinearLayout) inflater.inflate(R.layout.fragment_base, (ViewGroup) null);
        VB dataBinding = DataBindingUtil.inflate(inflater, this.getLayoutId(), container, false);
        this.mBinding = new AutoClearedValue(this, dataBinding);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
        (this.mBinding.get()).getRoot().setLayoutParams(params);
        LinearLayout rlContainer = this.rootView.findViewById(R.id.container);
        rlContainer.addView((this.mBinding.get()).getRoot());
        this.initView(this.rootView);
        return this.rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mViewModel = this.getViewModel();
        this.getLifecycle().addObserver(this.mViewModel);
    }

    protected BaseActivity getHoldingActivity() {
        return (BaseActivity) this.getActivity();
    }

    protected LinearLayout getRootView() {
        return this.rootView;
    }
}
