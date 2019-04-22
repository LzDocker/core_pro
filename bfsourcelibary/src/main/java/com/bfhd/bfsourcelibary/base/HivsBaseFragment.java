package com.bfhd.bfsourcelibary.base;

import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bfhd.bfsourcelibary.widget.WaitDialog;
import com.docker.core.base.BaseActivity;
import com.docker.core.base.BaseFragment;
import com.docker.core.base.BaseViewModel;

public abstract class HivsBaseFragment<VM extends BaseViewModel, VB extends ViewDataBinding> extends BaseFragment<VM, VB> {

    public WaitDialog waitDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
    }

    public void showWaitDialog(String message, boolean cancleable) {
        waitDialog = new WaitDialog(getHoldingActivity());
        waitDialog.show(getHoldingActivity(), message, cancleable, null);
    }

    public void hidWaitDialog() {
        if (getHoldingActivity() != null && waitDialog != null && waitDialog.isShowing()) {
            waitDialog.hide();
        }
    }


}
