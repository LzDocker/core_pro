package com.docker.corepro.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.util.Log;

import com.docker.common.widget.EmptyCommand;
import com.docker.common.widget.EmptyStatus;
import com.docker.core.base.BaseViewModel;
import com.docker.core.util.ViewEventResouce;
import com.scwang.smartrefresh.layout.binding.command.ReplyCommand;

import javax.inject.Inject;

public class SimpleViewModel extends BaseViewModel {

    @Inject
    public SimpleViewModel() {

    }

    public final ObservableInt emptystatus = new ObservableInt();

    public final ObservableBoolean enableRefresh = new ObservableBoolean();

    public final ObservableBoolean complete = new ObservableBoolean();

    public final ObservableBoolean enableLoadmore = new ObservableBoolean();

    public final EmptyCommand retrycommand = () -> {
        getData();
    };

    public final ReplyCommand onrefreshCommand = () -> {
        getData();
    };

    public final ReplyCommand onloadmoreCommand = () -> {
        getData();
    };

    public void getData() {
//        emptystatus.set(EmptyStatus.BdHiden);
        emptystatus.set(EmptyStatus.BdLoading);
    }

    @Override
    public void create() {
        super.create();
        emptystatus.set(EmptyStatus.BdError);
        enableRefresh.set(false);
        enableLoadmore.set(true);


        viewEventResouce.setValue(new ViewEventResouce(1, "11222111", 1333));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("sss", "onCleared: ---------------");
    }
}
