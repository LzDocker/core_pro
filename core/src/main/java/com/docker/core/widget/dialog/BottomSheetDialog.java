package com.docker.core.widget.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.docker.core.R;
import com.docker.core.BR;
import com.docker.core.adapter.SimpleCommonRecyclerAdapter;

import java.util.Arrays;

/**
 */
public class BottomSheetDialog extends DialogFragment {
    private Dialog mDialog;
    private ChooseCallback chooseCallback;
    private String[] items;
    private SimpleCommonRecyclerAdapter<String> stringSimpleCommonRecyclerAdapter = new SimpleCommonRecyclerAdapter<>(R.layout.item_choose_options, BR.item);

    public interface ChooseCallback {
        void onClickOptions(int position);
    }

    public void setDataCallback(String[] strs, ChooseCallback chooseCallback) {
        items = strs;
        this.chooseCallback = chooseCallback;
        stringSimpleCommonRecyclerAdapter.add(items);
        stringSimpleCommonRecyclerAdapter.notifyDataSetChanged();
    }

    public void replaceData(String[] strs){
        this.items = strs;
        stringSimpleCommonRecyclerAdapter.replace(Arrays.asList(items));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            mDialog = new Dialog(getActivity(), R.style.BottomDialog);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.dialog_fragment_choose_layout);
            mDialog.setCanceledOnTouchOutside(true);

            Window window = mDialog.getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.windowAnimations = R.style.AnimBottom;
            window.setAttributes(layoutParams);

            TextView tv_cancel = (TextView) mDialog.findViewById(R.id.dialog_fragment_choose_layout_tv_cancel);
            RecyclerView recyclerView = mDialog.findViewById(R.id.recycle);
            tv_cancel.setOnClickListener((v) -> {
                mDialog.dismiss();
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(stringSimpleCommonRecyclerAdapter);
            stringSimpleCommonRecyclerAdapter.setOnItemClickListener((v, p) -> {
                chooseCallback.onClickOptions(p);
            });

        return mDialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    public void show(FragmentActivity context) {
        this.show(context.getSupportFragmentManager(), "bottom");
    }


}
