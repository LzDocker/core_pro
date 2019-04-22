package com.docker.corepro.ui;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bfhd.bfsourcelibary.base.HivsBaseActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.docker.core.base.BaseActivity;
import com.docker.core.di.module.httpmodule.MHeader;
import com.docker.core.di.module.httpmodule.progress.ProgressListen;
import com.docker.core.di.module.httpmodule.progress.ProgressManager;
import com.docker.core.repository.Resource;
import com.docker.core.util.AppExecutors;
import com.docker.core.util.ViewEventResouce;
import com.docker.corepro.BuildConfig;
import com.docker.corepro.MainActivity;
import com.docker.corepro.R;
import com.docker.corepro.api.CommonService;
import com.docker.corepro.api.ServiceConfig;
import com.docker.corepro.databinding.ActivityAccountBinding;
import com.docker.corepro.viewmodel.AccountViewModel;
import com.docker.corepro.vo.LoginVo;
import com.docker.corepro.vo.RegisterVo;
import com.docker.updatelibary.environment.EnvironmentManager;
import com.docker.updatelibary.versioncontroler.VersionManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;
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
import retrofit2.Response;

public class AccountActivity extends HivsBaseActivity<AccountViewModel, ActivityAccountBinding> {


    @Inject
    ViewModelProvider.Factory factory;
    
    @Inject
    MHeader mHeader;

    @Inject
    AppExecutors appExecutors;

    @Inject
    ProgressManager progressManager;

    @Inject
    VersionManager versionManager;

    @Inject
    CommonService commonService;

    @Inject
    EnvironmentManager environmentManager;


    private static final int RegisterFlag = 1001;
    private static final int LoginFlag = 1002;
    private static int Flag = RegisterFlag;
    private RegisterVo registerVo;

    public void click(View view) {

        Intent intent = new Intent(AccountActivity.this, SimpleActivity.class);
        startActivity(intent);
//        testpickImage();
//        initUpdate();
//        bottomSheetDialog bottomSheetDialog = new bottomSheetDialog();
//        bottomSheetDialog.setDataCallback(new String[]{"111", "222"}, new bottomSheetDialog.ChooseCallback() {
//            @Override
//            public void onClickOptions(int position) {
//                bottomSheetDialog.dismiss();
//            }
//        });
//       bottomSheetDialog.show(this);
//        environmentManager.selectEnvironment();
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

        mHeader.setServerUrl(ServiceConfig.SERVER_URL_PRO);
//        initUpdate();
        initview();
        initEnviron();
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
    }

    private void initEnviron() {
        environmentManager.bind(this, this, new String[]{"测试", "线上"}, new String[]{ServiceConfig.SERVER_URL_TEST, ServiceConfig.SERVER_URL_PRO}, ServiceConfig.SERVER_URL_PRO);

    }

    private void initUpdate() {
        this.getLifecycle().addObserver(versionManager.Bind(this, this, mViewModel.checkUpdate(), VersionManager.TYPE_NOTIFYACTION));
    }


    private void initview() {

        mViewModel.getViewEventResouce().observe(this, new Observer<ViewEventResouce>() {
            @Override
            public void onChanged(@Nullable ViewEventResouce viewEventResouce) {
                Log.d("sss", "onChanged:--------- " + viewEventResouce.eventType + viewEventResouce.data + viewEventResouce.message);

            }
        });
//        boolean islogin = (boolean) SpTool.get(this, "LOGIN_FLAG", false);
        boolean islogin = (boolean) SPUtils.getInstance("eee").getBoolean("LOGIN_FLAG", false);
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

        mViewModel.loginlv.observe(this, loginVoResource -> {
//                showToast(loginVoResource.status.name());
            Log.d("ssss", "onChanged: --------login---------------" + loginVoResource.status.name());
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
//                showToast(loginVoResource.status.name());
            }
        });
//        mViewModel.registVo.observe(this, );
    }


    private void login() {
//        chechParam();
//        mViewModel.Login(registerVo.getUsername(), registerVo.getPassword());

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //申请的权限全部允许
                    Toast.makeText(AccountActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                    download();
                } else {
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

//        progressManager.progressDownLoad("qq.apk", new ProgressListener() {
//            @Override
//            public Call onProcessDownLoadMethod(Retrofit retrofit) {
//                downCall = retrofit.create(AccountService.class).downApk("http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk");
//                return downCall;
//            }
//
//            @Override
//            public Call onProcessUploadMethod(Map<String, RequestBody> params) {
//                return null;
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                mBinding.tvRegister.setText("onFailure");
//            }
//
//            @Override
//            public void onProgress(long progress, long total, boolean done) {
//                double aa = (progress * 1.0 / total * 100);
//                Log.d("sss", "progress: ------------------" + aa + "------------");
//                progressMld.postValue(new Double(aa).intValue());
//            }
//
//            @Override
//            public void onComplete(retrofit2.Response<ResponseBody> response) {
//                Log.d("sss", "onComplete: ------------------");
//                mBinding.tvRegister.setText("onComplete");
//                AppUtils.installApp(Environment.getExternalStorageDirectory()+"/qq.apk");
//            }
//        });


        progressManager.download(getCacheDir().getPath(), "qq.apk", "http://116.117.158.129/f2.market.xiaomi.com/download/AppStore/04275951df2d94fee0a8210a3b51ae624cc34483a/com.tencent.mm.apk", new ProgressListen() {
            @Override
            public void ondownloadStart(Call call) {
                downCall = call;
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
            public void onComplete(Response<ResponseBody> response) {
                mBinding.tvRegister.setText("onComplete");
                AppUtils.installApp(getCacheDir().getPath() + "/qq.apk");
//                installApk(new File(getCacheDir().getPath() + "/qq.apk"));
            }
        });

    }



    private void installApk(File file) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                apkUri = FileProvider.getUriForFile(AccountActivity.this
                        , "项目包名.FileProvider"
                        , file);
            } else {
                apkUri = Uri.fromFile(file);
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            // 查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置在 setDataAndType 方法之后
            List<ResolveInfo> resolveLists = AccountActivity.this.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            // 然后全部授权
            for (ResolveInfo resolveInfo : resolveLists){
                String packageName = resolveInfo.activityInfo.packageName;
                AccountActivity.this.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            AccountActivity.this.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downCall != null) {
            downCall.cancel();
        }
    }


    private void testpickImage() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(true)// 是否显示gif图片
//                .selectionMedia(selectList)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()
                .videoMaxSecond(300)//显示多少秒以内的视频or音频也可适用
                .recordVideoSecond(10)//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
}
