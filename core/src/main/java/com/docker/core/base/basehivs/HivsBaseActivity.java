package com.docker.core.base.basehivs;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.docker.core.base.BaseActivity;
import com.docker.core.util.ViewEventResouce;
import com.docker.core.widget.dialog.WaitDialog;

public abstract class HivsBaseActivity<VM extends HivsBaseViewModel, VB extends ViewDataBinding> extends BaseActivity<VM, VB> {

    public WaitDialog waitDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceStates) {
        super.onCreate(savedInstanceStates);
        initBasicListener();
    }

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
        waitDialog = new WaitDialog(this);
        waitDialog.show(this, message, cancleable, null);
    }

    public void hidWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.hide();
        }
    }


}
