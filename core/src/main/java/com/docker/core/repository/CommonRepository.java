package com.docker.core.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.di.module.cachemodule.CacheStrategy;
import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.core.util.AppExecutors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommonRepository {

    private final AppExecutors appExecutors;
    private final CacheDatabase cacheDatabase;

    @Inject
    public CommonRepository(AppExecutors appExecutors, CacheDatabase cacheDatabase) {
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
    }

    public <T> MediatorLiveData<Resource<T>> noneChache(LiveData<ApiResponse<BaseResponse<T>>> servicefun) {
        return new NetworkBoundResourceAuto<T>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return servicefun;
            }
        }.asLiveData();
    }

    public <T> MediatorLiveData<Resource<T>> ChacheFeatch(LiveData<ApiResponse<BaseResponse<T>>> servicefun, CacheStrategy cacheStrategy, String cashkey) {
        return new NetworkBoundResourceAuto<T>(appExecutors, cacheStrategy, cacheDatabase, cashkey) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return servicefun;
            }
        }.asLiveData();
    }

    public <T> MediatorLiveData<Resource<T>> SpecialFeatch(LiveData<ApiResponse<T>> servicefun) {
        return new NetworkBoundResourceAuto<T>(0) {
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<T>>> createCall() {
                return null;
            }
            @NonNull
            @Override
            protected LiveData<ApiResponse<T>>createSpecCall() {
                return servicefun;
            }
        }.asLiveData();
    }
}
