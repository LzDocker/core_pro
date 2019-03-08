package com.docker.corepro.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;
import com.docker.core.util.AppExecutors;
import com.docker.corepro.api.AccountService;
import com.docker.corepro.vo.LoginVo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhangxindang on 2018/12/24.
 */

@Singleton
public class AccountRepository {

    private final AppExecutors appExecutors;
    private final AccountService accountService;
    private final CacheDatabase cacheDatabase;

    @Inject
    public AccountRepository(AppExecutors appExecutors, AccountService accountService, CacheDatabase cacheDatabase) {
        this.accountService = accountService;
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
    }

    public LiveData<Resource<LoginVo>> Login(String username, String pwd) {
         return new NetworkBoundResourceAuto<LoginVo>(){
             @NonNull
             @Override
             protected LiveData<ApiResponse<BaseResponse<LoginVo>>> createCall() {
                 return accountService.login(username,pwd);
             }
         }.asLiveData();
    }

    public LiveData<Resource<LoginVo>> registe(String name , String pwd , String repwd){
        return new NetworkBoundResourceAuto<LoginVo>(){
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<LoginVo>>> createCall() {
                return accountService.register(name,pwd,repwd);
            }
        }.asLiveData();
    }


}
