package com.docker.core.util.callback;


import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.base.basehivs.HivsBaseViewModel;
import com.docker.core.repository.Resource;
import com.docker.core.widget.emptylayout.EmptyStatus;

/**
 * Created by zhangxindang on 2018/9/6.
 */

public abstract class NetBoundCallback<T> {


    public HivsBaseViewModel baseViewModel;

    public NetBoundCallback() {
    }

    public NetBoundCallback(HivsBaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    /*
     * 缓存读取成功
     * */
    public void onCacheComplete(T Result) {
        if (baseViewModel != null) {
            baseViewModel.mEmptycommand.set(EmptyStatus.BdHiden);
            baseViewModel.mCompleteCommand.set(false);
            baseViewModel.mEnableLoadmore.set(false);
            baseViewModel.mEnableRefresh.set(false);
        }
    }

    public void onComplete(Resource<T> resource) {
        if (baseViewModel != null) {
            if (baseViewModel.mPage == 1 && resource.data == null) {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdEmpty);
                baseViewModel.mCompleteCommand.set(false);
                baseViewModel.mEnableLoadmore.set(false);
                baseViewModel.mEnableRefresh.set(false);
            } else {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdHiden);
                baseViewModel.mCompleteCommand.set(true);
                baseViewModel.mEnableLoadmore.set(true);
                baseViewModel.mEnableRefresh.set(true);
            }
        }
    }

    public void onComplete() {
    }

    public void onBusinessError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        }
        if (baseViewModel != null) {

            if (baseViewModel.mPage == 1 && resource.data == null) {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdEmpty);
                baseViewModel.mCompleteCommand.set(false);
                baseViewModel.mEnableLoadmore.set(false);
                baseViewModel.mEnableRefresh.set(false);
            } else {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdHiden);
                baseViewModel.mCompleteCommand.set(true);
                baseViewModel.mEnableLoadmore.set(true);
                baseViewModel.mEnableRefresh.set(true);
            }
        }
    }

    ;

    public void onNetworkError(Resource<T> resource) {
        if (resource.message != null) {
            ToastUtils.showShort(resource.message);
        }
        if (baseViewModel != null) {
            if (baseViewModel.mPage == 1 && resource.data == null) {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdError);
                baseViewModel.mCompleteCommand.set(false);
                baseViewModel.mEnableLoadmore.set(false);
                baseViewModel.mEnableRefresh.set(false);
            } else {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdHiden);
                baseViewModel.mCompleteCommand.set(true);
                baseViewModel.mEnableLoadmore.set(true);
                baseViewModel.mEnableRefresh.set(true);
            }
        }
    };

    public void onLoading() {
        if (baseViewModel != null && baseViewModel.mPage == 1) {
            if (baseViewModel.mIsfirstLoad) {
                baseViewModel.mEmptycommand.set(EmptyStatus.BdLoading);
            }
            baseViewModel.mCompleteCommand.set(false);
            baseViewModel.mEnableLoadmore.set(false);
            baseViewModel.mEnableRefresh.set(false);
        }
    }

    ;
}
