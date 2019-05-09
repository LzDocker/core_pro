package com.docker.core.util.callback;


import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.repository.Resource;

/**
 * Created by zhangxindang on 2018/9/6.
 */

public abstract class NetBoundCallback<T> {

    public NetBoundCallback() {
    }

    /*
     * 缓存读取成功
     * */
    public void onCacheComplete(T Result) {
    }

    public void onComplete(Resource<T> resource) {
    }

    public void onComplete() {
    }

    public void onBusinessError(Resource<T> resource) {
        if(resource.message!=null){
            ToastUtils.showShort(resource.message);
        }

    };

    public void onNetworkError(Resource<T> resource) {
        if(resource.message!=null){
            ToastUtils.showShort(resource.message);
        }
    }

    ;

    public void onLoading() {
    }

    ;
}
