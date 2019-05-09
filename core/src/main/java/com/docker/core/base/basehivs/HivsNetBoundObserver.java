package com.docker.core.base.basehivs;

import android.support.annotation.Nullable;

import com.docker.core.repository.Resource;
import com.docker.core.util.callback.NetBoundCallback;
import com.docker.core.widget.emptylayout.EmptyStatus;

public  class HivsNetBoundObserver<T> implements android.arch.lifecycle.Observer<Resource<T>> {


    public HivsBaseViewModel baseViewModel;

    private NetBoundCallback<T> netBoundCallback;

    public HivsNetBoundObserver(NetBoundCallback<T> netBoundCallback) {
        this.netBoundCallback = netBoundCallback;
    }

    public HivsNetBoundObserver(NetBoundCallback<T> netBoundCallback,
                                HivsBaseViewModel baseViewModel) {
        this.netBoundCallback = netBoundCallback;
        this.baseViewModel = baseViewModel;
    }

    @Override
    public void onChanged(@Nullable Resource<T> tResource) {

        switch (tResource.status) {
            case LOADING:
                if (tResource.data != null) {
                    netBoundCallback.onCacheComplete(tResource.data);
                    if (baseViewModel != null) {
                        baseViewModel.mEmptycommand.set(EmptyStatus.BdHiden);
                        baseViewModel.mCompleteCommand.set(false);
                        baseViewModel.mEnableLoadmore.set(false);
                        baseViewModel.mEnableRefresh.set(false);
                    }
                } else {
                    netBoundCallback.onLoading();
                    if (baseViewModel != null && baseViewModel.mPage == 1) {
//                        baseViewModel.mEmptycommand.set(EmptyStatus.BdLoading);
                        baseViewModel.mCompleteCommand.set(false);
                        baseViewModel.mEnableLoadmore.set(false);
                        baseViewModel.mEnableRefresh.set(false);
                    }
                }
                break;
            case SUCCESS:
                netBoundCallback.onComplete(tResource);
                if (baseViewModel != null) {

                    if (baseViewModel.mPage == 1 && tResource.data == null) {
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

                break;
            case BUSSINESSERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onBusinessError(tResource);
                if (baseViewModel != null) {

                    if (baseViewModel.mPage == 1 && tResource.data == null) {
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

                break;
            case ERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onNetworkError(tResource);

                if (baseViewModel != null) {

                    if (baseViewModel.mPage == 1 && tResource.data == null) {
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

                break;
        }
    }


}
