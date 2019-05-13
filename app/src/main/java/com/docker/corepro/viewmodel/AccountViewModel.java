package com.docker.corepro.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.docker.core.base.basehivs.HivsBaseViewModel;
import com.docker.core.repository.CommonRepository;
import com.docker.core.repository.Resource;
import com.docker.core.util.ViewEventResouce;
import com.docker.core.util.callback.NetBoundCallback;
import com.docker.core.util.callback.NetBoundObserver;
import com.docker.core.util.versioncontrol.vo.UpdateInfo;
import com.docker.corepro.api.AccountService;
import com.docker.corepro.repository.AccountRepository;
import com.docker.corepro.vo.LoginParam;
import com.docker.corepro.vo.LoginVo;
import com.docker.corepro.vo.RegisterVo;
import com.docker.corepro.vo.SpecLoginVo;

import javax.inject.Inject;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class AccountViewModel extends HivsBaseViewModel {

    @Inject
    AccountService service;

    @Inject
    AccountRepository accountRepository;

    @Inject
    CommonRepository commonRepository;


    @Inject
    public AccountViewModel() {

    }

    private final MutableLiveData<LoginParam> paramlv = new MutableLiveData();

    public final LiveData<Resource<SpecLoginVo>> loginlv =
            Transformations.switchMap(paramlv, new Function<LoginParam, LiveData<Resource<SpecLoginVo>>>() {
                @Override
                public LiveData<Resource<SpecLoginVo>> apply(LoginParam param) {
                    return accountRepository.Login("https://www.wanandroid.com/user/login", param.name, param.pwd);
                }
            });


    public void Login(String username, String pwd) {
        mVmEventSouce.setValue(new ViewEventResouce(1, "11111111", 1));

        paramlv.setValue(new LoginParam(username, pwd));
    }


    /*
     * 注册
     * */
//    public LiveData<ApiResponse<BaseResponse<LoginVo>>> register(RegisterVo registerVo) {
//
//        return service.register(registerVo.getUsername().toString().trim(),
//                registerVo.getPassword().toString().trim(), registerVo.getRepassword().toString().trim());
//    }

    private final MutableLiveData<RegisterVo> registerParm = new MutableLiveData<>();

    public LiveData<Resource<LoginVo>> register(RegisterVo registerVo) {
        registerParm.setValue(registerVo);
        showDialogWait("11111", true);

        return registVo;

    }

    public final LiveData<Resource<LoginVo>> registVo = Transformations.switchMap(registerParm, new Function<RegisterVo, LiveData<Resource<LoginVo>>>() {
        @Override
        public LiveData<Resource<LoginVo>> apply(RegisterVo input) {
//            return accountRepository.registe(input.getUsername(), input.getPassword(), input.getRepassword());

            return commonRepository.noneChache(service.register(input.getUsername(), input.getPassword(), input.getRepassword()));
        }
    });

    /*
     *
     * 更新
     * */
    public final LiveData<Resource<UpdateInfo>> checkUpdate() {
        return accountRepository.checkUpData();
    }

    public void registerqq(RegisterVo input) {

        mResourceLiveData.addSource(commonRepository.noneChache(service.register(input.getUsername(), input.getPassword(),
                input.getRepassword())), new NetBoundObserver<>(new NetBoundCallback<LoginVo>() {


            @Override
            public void onBusinessError(Resource<LoginVo> resource) {

            }

            @Override
            public void onNetworkError(Resource<LoginVo> resource) {

            }
        }));

    }

    @Override
    public void initCommand() {

    }
}
