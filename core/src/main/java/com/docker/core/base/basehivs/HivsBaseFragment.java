package com.docker.core.base.basehivs;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.docker.core.base.BaseFragment;
import com.docker.core.util.ViewEventResouce;
import com.docker.core.widget.dialog.DialogWait;

public abstract class HivsBaseFragment<VM extends HivsBaseViewModel, VB extends ViewDataBinding> extends BaseFragment<VM, VB> {

    public DialogWait dialogWait;

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
                    showWaitDialog(viewEventResouce.message);
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

    public void showWaitDialog(String message) {
        if (dialogWait == null) {
            dialogWait = new DialogWait(getHoldingActivity());
        }
        dialogWait.setMessage(message);
        dialogWait.show();
    }

    public void hidWaitDialog() {
        if (getHoldingActivity() != null && dialogWait != null) {
            dialogWait.dismiss();
        }
    }


}
