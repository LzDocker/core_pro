package com.docker.core.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.docker.core.R;
import com.docker.core.databinding.DialogWaitingBinding;

public class DialogWait extends BaseDialog<DialogWaitingBinding> {
    public DialogWait(Context context) {
        super(context, R.style.DialogStyle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_waiting;
    }

    @Override
    public void setMessage(String message) {
        mBinding.setMessage(message);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
    }
}
