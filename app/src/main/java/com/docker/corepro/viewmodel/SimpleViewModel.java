package com.docker.corepro.viewmodel;
import com.docker.core.base.basehivs.HivsBaseViewModel;
import com.docker.core.util.ViewEventResouce;
import com.docker.core.widget.emptylayout.EmptyStatus;

import javax.inject.Inject;

public class SimpleViewModel extends HivsBaseViewModel {

    @Inject
    public SimpleViewModel() { }


    @Override
    public void initCommand() {
        mCommand.OnRefresh(() -> {   getData();});
        mCommand.OnLoadMore(() -> {   mEmptycommand.set(EmptyStatus.BdLoading);});
        mCommand.OnRetryLoad(() -> {  getData(); });
    }


    public void getData() {
        mEmptycommand.set(EmptyStatus.BdHiden);
    }

    @Override
    public void create() {
        super.create();
        mEnableRefresh.set(false);
        mEnableLoadmore.set(true);
        mVmEventSouce.setValue(new ViewEventResouce(1, "11222111", 1333));

        showDialogWait("11111",true);
        hideDialogWait();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public String img = "https://wanandroid.com/blogimgs/54f4350f-039d-48b6-a38b-0933e1405004.png";


}
