package com.docker.core.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.di.module.cachemodule.CacheEntity;
import com.docker.core.di.module.cachemodule.CacheStrategy;
import com.docker.core.di.module.httpmodule.ApiResponse;
import com.docker.core.di.module.httpmodule.BaseResponse;
import com.docker.core.util.AppExecutors;
import com.docker.core.util.IOUtils;



/**
 * Created by zhangxindang on 2018/12/25.
 */

public abstract class NetworkBoundResourceAuto<ResultType> {
    private AppExecutors appExecutors;
    private CacheStrategy cacheStrategy;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private String cachekey;
    private CacheDatabase cacheDatabase;

    /*
     * 需要缓存的构造
     * */
    @MainThread
    public NetworkBoundResourceAuto(AppExecutors appExecutors, CacheStrategy cacheStrategy, CacheDatabase cacheDatabase, String cachekey) {
        this.appExecutors = appExecutors;
        this.cacheStrategy = cacheStrategy;
        this.cacheDatabase = cacheDatabase;
        this.cachekey = cachekey;
        startFetch();
    }

    /*
     * 不需要缓存的构造
     * */
    @MainThread
    public NetworkBoundResourceAuto() {
        this.cacheStrategy = CacheStrategy.NO_CACHE;
        NO_ChCHE();
    }

    /*
     * 回调 ---》
     *
     * ===> loading ===  bussiness error / networkerror
     *
     * ===>  loading === success
     * */
    private void NO_ChCHE() {
        setZoneValue(Resource.loading(null,null));
        LiveData<ApiResponse<BaseResponse<ResultType>>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            if (response.isSuccessful()&&response.body!=null) {
                if (response!=null &&response.body.getErrno()!=null&&Integer.parseInt(response.body.getErrno())<0) { // bussiness error
                    result.addSource(apiResponse,
                            newData -> {
                                result.removeSource(apiResponse);
                                setZoneValue(Resource.bussinessError(response.body.getErrmsg(), null));
                            });
                } else {
                    setZoneValue(Resource.success((ResultType) response.body.getRst()));
                }
            } else {
                onFetchFailed();
                result.addSource(apiResponse,
                        newData -> {
                            result.removeSource(apiResponse);
                            setZoneValue(Resource.error(response.errorMessage, null));
                        });
            }
        });

    }
    /*
    FIRST_CACHE_THEN_REQUEST
     * 回调 ---》 第二个loading带着缓存数据，但缓存不保证不为空
     *
     * ===> loading === loading====> bussiness error / networkerror
     *
     * ===>  loading ===loading ===> success
     * */


    /* IF_NONE_CACHE_REQUEST
     * 回调 ---》 有缓存直接返回，没有就请求网络 success 中包含的数据就是可用数据
     *
     * ===> loading ====> bussiness error / networkerror
     *
     * ===>  loading  ===> success
     * */

    /*
    REQUEST_FAILED_READ_CACHE
     * 回调 ---》 网络成功 ---》 loading ---- success
     *           网络失败 ---》 loading ----- loading(数据为空，包含网络出错原因) ---> success(可能是空的缓存)
     * */


    private void startFetch() {
        setZoneValue(Resource.loading(null,null));
        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                fetchFromdb();
                break;
            case FIRST_CACHE_THEN_REQUEST:
                fetchFromdb();
                break;
            case REQUEST_FAILED_READ_CACHE:
                fetchFromNetwork();
                break;
        }
    }

    private void fetchFromdb() {
        LiveData<ApiResponse<BaseResponse<ResultType>>> dbSource = loadFromDb();
        result.addSource(dbSource, newdata -> {
            result.removeSource(dbSource);
            if (newdata != null) {
                onFetchDbSuccess(newdata);
            } else {
                onFetchDbFiled();
            }
        });
    }

    private void onFetchDbFiled() {
        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                fetchFromNetwork();
                break;
            case FIRST_CACHE_THEN_REQUEST:
                setZoneValue(Resource.loading(null,null));
                fetchFromNetwork();
                break;
            case REQUEST_FAILED_READ_CACHE:
                setZoneValue(Resource.error("-1", null)); // 没有缓存
                break;
        }
    }

    private void onFetchDbSuccess(ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                break;
            case FIRST_CACHE_THEN_REQUEST:
                setZoneValue(Resource.loading(null,(ResultType) newdata.body.getRst()));
                fetchFromNetwork();
                break;
            case REQUEST_FAILED_READ_CACHE:
                setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                break;
        }

    }


    private void fetchFromNetwork() {
        LiveData<ApiResponse<BaseResponse<ResultType>>> apiResponse = createCall();
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            if (response.isSuccessful()) {
                if (Integer.parseInt(response.body.getErrno())<0) { // bussiness error
                    onFetchNetFailed(0, response);
                } else {
                    appExecutors.diskIO().execute(() -> {
                        saveCallResult(response);
                        appExecutors.mainThread().execute(() -> {
                                    LiveData<ApiResponse<BaseResponse<ResultType>>> dbSource1 = loadFromDb();
                                    result.addSource(dbSource1,
                                            newData -> {
                                                result.removeSource(dbSource1);
                                                onFetchNetSuccess(newData);
                                            });
                                }
                        );
                    });
                }
            } else {
                onFetchFailed();
                onFetchNetFailed(1, response);
            }
        });
    }


    /*
     * 1  error
     * 0  bussiness error
     * */
    private void onFetchNetFailed(int errType, ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                if (errType == 1) {
                    setZoneValue(Resource.error(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.bussinessError(newdata.body.getErrmsg(), null));
                }
                break;
            case FIRST_CACHE_THEN_REQUEST:
                if (errType == 1) {
                    setZoneValue(Resource.error(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.bussinessError(newdata.body.getErrmsg(), null));
                }

                break;
            case REQUEST_FAILED_READ_CACHE:
                if (errType == 1) {
                    setZoneValue(Resource.loading(newdata.errorMessage, null));
                } else {
                    setZoneValue(Resource.loading(newdata.body.getErrmsg(), null));
                }
               fetchFromdb();
                break;
        }

    }


    private void onFetchNetSuccess(ApiResponse<BaseResponse<ResultType>> newdata) {

        switch (cacheStrategy) {
            case IF_NONE_CACHE_REQUEST:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
                break;
            case FIRST_CACHE_THEN_REQUEST:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
                break;
            case REQUEST_FAILED_READ_CACHE:
                if (newdata != null) {
                    setZoneValue(Resource.success((ResultType) newdata.body.getRst()));
                } else {
                    setZoneValue(Resource.success(null));
                }
               fetchFromdb();
                break;
        }

    }


    @MainThread
    private void setZoneValue(Resource<ResultType> newValue) {
        Log.w("ZoneValue", ": -----------setZoneValue----------" + newValue.status);
        result.setValue(newValue);
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private void saveCallResult(@NonNull ApiResponse<BaseResponse<ResultType>> response) {
        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setKey(cachekey);
        cacheEntity.setData(IOUtils.toByteArray(response));
        cacheDatabase.cacheEntityDao().insertCache(cacheEntity);
    }

    @NonNull
    @MainThread
    private LiveData<ApiResponse<BaseResponse<ResultType>>> loadFromDb() {
        final MediatorLiveData<ApiResponse<BaseResponse<ResultType>>> responseMediatorLiveData = new MediatorLiveData<>();
        LiveData<CacheEntity> souce = cacheDatabase.cacheEntityDao().LoadCache(cachekey);
        responseMediatorLiveData.addSource(souce, newdata -> {
            responseMediatorLiveData.removeSource(souce);
            if (newdata != null && newdata.getData() != null) {
                responseMediatorLiveData.setValue((ApiResponse<BaseResponse<ResultType>>) IOUtils.toObject(newdata.getData()));
            } else {
                responseMediatorLiveData.setValue(null);
            }
        });
        return responseMediatorLiveData;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<BaseResponse<ResultType>>> createCall();
}

