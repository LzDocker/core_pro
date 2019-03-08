package com.docker.corepro.ui;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.docker.core.base.BaseActivity;
import com.docker.core.di.module.httpmodule.MHeader;
import com.docker.core.di.module.httpmodule.progress.ProgressListener;
import com.docker.core.di.module.httpmodule.progress.ProgressManager;
import com.docker.core.repository.Resource;
import com.docker.core.util.AppExecutors;
import com.docker.core.util.SpTool;
import com.docker.corepro.R;
import com.docker.corepro.api.AccountService;
import com.docker.corepro.databinding.ActivityAccountBinding;
import com.docker.corepro.viewmodel.AccountViewModel;
import com.docker.corepro.vo.LoginVo;
import com.docker.corepro.vo.RegisterVo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountActivity extends BaseActivity<AccountViewModel, ActivityAccountBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    MHeader mHeader;

    @Inject
    AppExecutors appExecutors;

    @Inject
    ProgressManager progressManager;


    private static final int RegisterFlag = 1001;
    private static final int LoginFlag = 1002;
    private static int Flag = RegisterFlag;
    private RegisterVo registerVo;

    public void click(View view) {
        mHeader.setServerUrl("http://www.baidu.com/");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public AccountViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(AccountViewModel.class);
    }

    @Override
    protected void inject() {
        super.inject();
        ARouter.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        initview();
         mToolbar.setTitle("登录");
         mToolbar.setNavigation(false);


        progressMld.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                mBinding.tvRegister.setText(integer + "%");
                Log.d("sss", "progress: -----------onChanged-------" + integer + "------------");
            }
        });
//        mBinding.empty.showLoading();
//        mBinding.empty.showError();
//        mBinding.empty.showNoData();
//    s
    }

    private void initview() {
        boolean islogin = (boolean) SpTool.get(this, "LOGIN_FLAG", false);
        if (islogin) {
            toHome(null);
            finish();
            return;
        }
        registerVo = new RegisterVo();
        mBinding.setVo(registerVo);
        Flag = getIntent().getIntExtra("FLAG", RegisterFlag);
        if (Flag == LoginFlag) {
            mBinding.llVerCoutainer.setVisibility(View.GONE);
            mBinding.tvRegister.setText("登录");
            mBinding.tvLogin.setVisibility(View.GONE);
        }
        mBinding.tvRegister.setOnClickListener(v -> {
//            login();
            if (chechParam()) {
                if (Flag == RegisterFlag) {
                    register();
                } else {
                    login();
                }
            }
        });
        mBinding.tvLogin.setOnClickListener(v -> {
            toLogin();
        });

        mViewModel.loginlv.observe(this, new Observer<Resource<LoginVo>>() {
            @Override
            public void onChanged(@Nullable Resource<LoginVo> loginVoResource) {
                showToast(loginVoResource.status.name());

            }
        });
    }


    private boolean chechParam() {
        registerVo = mBinding.getVo();
        if (TextUtils.isEmpty(registerVo.getUsername())) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(registerVo.getPassword())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Flag == LoginFlag) {
            return true;
        }
        if (TextUtils.isEmpty(registerVo.getRepassword())) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!registerVo.getRepassword().equals(registerVo.getPassword())) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void register() {
        chechParam();
        mViewModel.register(registerVo).observe(this, new Observer<Resource<LoginVo>>() {
            @Override
            public void onChanged(@Nullable Resource<LoginVo> loginVoResource) {
                showToast(loginVoResource.status.name());
            }
        });
//        mViewModel.registVo.observe(this, );
    }


    private void login() {
//        chechParam();
//        mViewModel.Login(registerVo.getUsername(), registerVo.getPassword());

        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    //申请的权限全部允许
                    Toast.makeText(AccountActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                    download();
                }else{
                    //只要有一个权限被拒绝，就会执行
                    Toast.makeText(AccountActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void toLogin() {

        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("FLAG", LoginFlag);
        startActivity(intent);
        finish();

    }


    private void toHome(LoginVo Result) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(ConstantsRouter.ModuleAccount.LOGINVO, Result);
//        ARouter.getInstance().build(ConstantsRouter.ModulePlayer.ACTIVITY_MAIN).withBundle(ConstantsRouter.ModuleAccount.LOGINVO, bundle).navigation();

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onComplete();
            }
        }).safeSubscribe(new io.reactivex.Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private MutableLiveData<Integer> progressMld = new MutableLiveData<>();
    private Call downCall;

    // 测试下载文件
    private void download() {

        progressManager.progressDownLoad("qq.apk", new ProgressListener() {
            @Override
            public Call onProcessDownLoadMethod(Retrofit retrofit) {
                downCall = retrofit.create(AccountService.class).downApk();
                return downCall;
            }

            @Override
            public Call onProcessUploadMethod(Map<String, RequestBody> params) {
                return null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mBinding.tvRegister.setText("onFailure");
            }

            @Override
            public void onProgress(long progress, long total, boolean done) {
                double aa = (progress * 1.0 / total * 100);
                Log.d("sss", "progress: ------------------" + aa + "------------");
                progressMld.postValue(new Double(aa).intValue());
            }

            @Override
            public void onComplete(retrofit2.Response<ResponseBody> response) {
                Log.d("sss", "onComplete: ------------------");
                mBinding.tvRegister.setText("onComplete");
                AppUtils.installApp(Environment.getExternalStorageDirectory()+"/qq.apk");
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downCall != null) {
            downCall.cancel();
        }
    }
}
