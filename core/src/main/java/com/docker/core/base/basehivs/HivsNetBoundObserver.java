package com.docker.core.base.basehivs;

import android.support.annotation.Nullable;

import com.docker.core.repository.Resource;
import com.docker.core.util.callback.NetBoundCallback;
import com.docker.core.widget.emptylayout.EmptyStatus;

public class HivsNetBoundObserver<T> implements android.arch.lifecycle.Observer<Resource<T>> {



    private NetBoundCallback<T> netBoundCallback;
    public HivsNetBoundObserver(NetBoundCallback<T> netBoundCallback) {
        this.netBoundCallback = netBoundCallback;
    }
    @Override
    public void onChanged(@Nullable Resource<T> tResource) {
        switch (tResource.status) {
            case LOADING:
                if (tResource.data != null) {
                    netBoundCallback.onCacheComplete(tResource.data);
                } else {
                    netBoundCallback.onLoading();
                }
                break;
            case SUCCESS:
                netBoundCallback.onComplete(tResource);
                break;
            case BUSSINESSERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onBusinessError(tResource);
                break;
            case ERROR:
                netBoundCallback.onComplete();
                netBoundCallback.onNetworkError(tResource);
                break;
        }
    }


}
