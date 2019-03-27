package com.docker.corepro.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.core.repository.NetworkBoundResourceAuto;
import com.docker.core.repository.Resource;
import com.docker.core.util.AppExecutors;
import com.docker.corepro.api.AccountService;
import com.docker.corepro.api.CommonService;
import com.docker.corepro.vo.LoginVo;
import com.docker.corepro.vo.SpecLoginVo;
import com.docker.updatelibary.vo.UpdateInfo;

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
    CommonService commonService;


    @Inject
    public AccountRepository(AppExecutors appExecutors, AccountService accountService, CacheDatabase cacheDatabase) {
        this.accountService = accountService;
        this.appExecutors = appExecutors;
        this.cacheDatabase = cacheDatabase;
    }

//    public LiveData<Resource<LoginVo>> Login(String username, String pwd) {
//         return new NetworkBoundResourceAuto<LoginVo>(){
//             @NonNull
//             @Override
//             protected LiveData<ApiResponse<BaseResponse<LoginVo>>> createCall() {
//                 return accountService.login(username,pwd);
//             }
//         }.asLiveData();
//    }

    public LiveData<Resource<SpecLoginVo>> Login(String url,String username, String pwd) {
        return new NetworkBoundResourceAuto<SpecLoginVo>(1){
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<SpecLoginVo>>> createCall() {
                return null;
            }
            @NonNull
            @Override
            protected LiveData<ApiResponse<SpecLoginVo>> createSpecCall() {
                return accountService.login(url,username,pwd);
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

    public LiveData<Resource<UpdateInfo>> checkUpData(){
        return new NetworkBoundResourceAuto<UpdateInfo>(){
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<UpdateInfo>>> createCall() {
                return commonService.systemUpdate("2","1",AppUtils.getAppVersionCode()+"");
            }
        }.asLiveData();
    }






}
