package com.docker.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.docker.common.R;
import com.docker.common.databinding.CommonEmptyViewBinding;

public class EmptyLayout extends LinearLayout {


    private int status;

    private static class innerBuilder {

        private static int[] imgres;
        private static String[] textstr;
        private static int[] textcolor;

    }

    CommonEmptyViewBinding mbinding;

    public EmptyLayout(Context context) {
        this(context, null, 0);
    }

    public EmptyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.empty_layout);
        status = ta.getInt(R.styleable.empty_layout_status, EmptyStatus.BdHiden);

        setOrientation(LinearLayout.VERTICAL);
        mbinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.common_empty_view, this, true);
        if (innerBuilder.textstr != null && !TextUtils.isEmpty(innerBuilder.textstr[0])) {
            mbinding.commonIvLoading.setBackgroundDrawable(context.getResources().getDrawable(innerBuilder.imgres[0]));
            mbinding.commonIvError.setBackgroundDrawable(context.getResources().getDrawable(innerBuilder.imgres[1]));
            mbinding.commonIvNodata.setBackgroundDrawable(context.getResources().getDrawable(innerBuilder.imgres[2]));

            mbinding.commonTvLoading.setText(innerBuilder.textstr[0]);
            mbinding.commonTvError.setText(innerBuilder.textstr[1]);
            mbinding.commonTvNodata.setText(innerBuilder.textstr[2]);

            mbinding.commonTvLoading.setTextColor(context.getResources().getColor(innerBuilder.textcolor[0]));
            mbinding.commonTvError.setTextColor(context.getResources().getColor(innerBuilder.textcolor[1]));
            mbinding.commonTvNodata.setTextColor(context.getResources().getColor(innerBuilder.textcolor[2]));
        } else {
//            ToastUtils.showShort("请先设置资源---emptylayout");
        }

    }

    public static void setDefaultViewRes(int[] imgres, String[] textstr, int[] textcolor) {
        innerBuilder.imgres = imgres;
        innerBuilder.textstr = textstr;
        innerBuilder.textcolor = textcolor;
    }

    public void showLoading() {
        mbinding.commonEmptyCoutainer.setBackgroundColor(getResources().getColor(R.color.common_white));
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) == mbinding.commonEmptyCoutainer) {
                this.getChildAt(i).setVisibility(VISIBLE);
            } else {
                this.getChildAt(i).setVisibility(GONE);
            }
        }
        for (int i = 0; i < mbinding.commonEmptyCoutainer.getChildCount(); i++) {
            mbinding.commonEmptyCoutainer.getChildAt(i).setVisibility(GONE);
        }
        mbinding.commonLoadingView.setVisibility(VISIBLE);
        for (int i = 0; i < mbinding.commonLoadingView.getChildCount(); i++) {
            mbinding.commonLoadingView.getChildAt(i).setVisibility(VISIBLE);
        }

    }

    public void showError() {
        mbinding.commonEmptyCoutainer.setBackgroundColor(getResources().getColor(R.color.common_bg));
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) == mbinding.commonEmptyCoutainer) {
                this.getChildAt(i).setVisibility(VISIBLE);
            } else {
                this.getChildAt(i).setVisibility(GONE);
            }
        }
        for (int i = 0; i < mbinding.commonEmptyCoutainer.getChildCount(); i++) {
            mbinding.commonEmptyCoutainer.getChildAt(i).setVisibility(GONE);
        }
        mbinding.commonErrView.setVisibility(VISIBLE);
        for (int i = 0; i < mbinding.commonErrView.getChildCount(); i++) {
            mbinding.commonErrView.getChildAt(i).setVisibility(VISIBLE);
        }
        mbinding.commonTvError.setOnClickListener(v -> {
            if (onretryListener != null) {
                onretryListener.onretry();
            }
        });
    }

    public void showNoData() {
        mbinding.commonEmptyCoutainer.setBackgroundColor(getResources().getColor(R.color.common_bg));
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) == mbinding.commonEmptyCoutainer) {
                this.getChildAt(i).setVisibility(VISIBLE);
            } else {
                this.getChildAt(i).setVisibility(GONE);
            }
        }
        for (int i = 0; i < mbinding.commonEmptyCoutainer.getChildCount(); i++) {
            mbinding.commonEmptyCoutainer.getChildAt(i).setVisibility(GONE);
        }
        mbinding.commonEmptyView.setVisibility(VISIBLE);
        for (int i = 0; i < mbinding.commonEmptyView.getChildCount(); i++) {
            mbinding.commonEmptyView.getChildAt(i).setVisibility(VISIBLE);
        }
    }

    public void hide() {
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) == mbinding.commonEmptyCoutainer) {
                this.getChildAt(i).setVisibility(GONE);
            } else {
                this.getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }

    /*
     * 点击重试留给外部调用
     * */
    public TextView getRetryView() {
        return mbinding.commonTvError;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        switch (status) {
            case EmptyStatus.BdError:
                this.showError();
                break;
            case EmptyStatus.BdEmpty:
                this.showNoData();
                break;
            case EmptyStatus.BdLoading:
                this.showLoading();
                break;
            case EmptyStatus.BdHiden:
                this.hide();
                break;
        }
    }

    @BindingAdapter("statusAttrChanged")
    public static void setStatusAttrChanged(EmptyLayout view, InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setListener(null);
        } else {
            view.setListener(inverseBindingListener::onChange);
        }
    }

    @BindingAdapter(value = "status", requireAll = false)
    public static void setstatus(EmptyLayout view, int status) {
        view.setStatus(status);

    }

    @InverseBindingAdapter(attribute = "status", event = "statusAttrChanged")
    public static int getStatus(EmptyLayout view) {
        return view.getStatus();
    }

    private OnStatusChangeListener listener;

    public interface OnStatusChangeListener {
        void onStatusChanged();
    }

    public void setListener(OnStatusChangeListener listener) {
        this.listener = listener;
    }

    public interface OnretryListener {
        void onretry();
    }

    private OnretryListener onretryListener;

    public OnretryListener getOnretryListener() {
        return onretryListener;
    }

    public void setOnretryListener(OnretryListener onretryListener) {
        this.onretryListener = onretryListener;
    }
}
