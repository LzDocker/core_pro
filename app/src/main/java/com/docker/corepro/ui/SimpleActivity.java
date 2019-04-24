package com.docker.corepro.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.docker.core.adapter.SimpleCommonRecyclerAdapter;
import com.docker.core.base.basehivs.HivsBaseActivity;
import com.docker.core.util.ViewEventResouce;
import com.docker.corepro.BR;
import com.docker.corepro.R;
import com.docker.corepro.databinding.ActivitySimpleBinding;
import com.docker.corepro.viewmodel.SimpleViewModel;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import javax.inject.Inject;

public class SimpleActivity extends HivsBaseActivity<SimpleViewModel, ActivitySimpleBinding> {


    @Inject
    ViewModelProvider.Factory factory;

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple;
    }

    @Override
    public SimpleViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SimpleViewModel.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isOverrideContentView = true;
        super.onCreate(savedInstanceState);
        initView();

    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarView(R.id.top_view)
                .navigationBarColor(R.color.colorPrimary)
                .fullScreen(true)
                .addTag("PicAndColor")
                .init();
    }

    private void initView(){

//        mViewModel.setEmptyCommand(mEmptycommand);
        mViewModel.getViewEventResouce().observe(this, new Observer<ViewEventResouce>() {
            @Override
            public void onChanged(@Nullable ViewEventResouce viewEventResouce) {
                Log.d("sss", "onChanged:--------- "+viewEventResouce.eventType+viewEventResouce.data+viewEventResouce.message);

            }
        });
        mBinding.setViewmodel(mViewModel);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleCommonRecyclerAdapter adapter =  new SimpleCommonRecyclerAdapter(R.layout.item_test_rev,BR.clientitem);
        mBinding.recyclerView.setAdapter(adapter);

//        mBinding.recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
//        mBinding.recyclerView.setRefreshProgressStyle(ProgressStyle.BallGridPulse);
//        View header = LayoutInflater.from(this).inflate(R.layout.header_index, (ViewGroup) this.findViewById(android.R.id.content), false);
//
//        mBinding.recyclerView.addHeaderView(header);
        ArrayList<String> arrayList = new ArrayList();
        for (int i = 0; i <30 ; i++) {
            arrayList.add("-----------------------"+i);
        }
        adapter.add(arrayList);
//        mBinding.empty.showError();
//        mBinding.empty.getRetryView().setOnClickListener(v->{mBinding.empty.hide();});
    }
}
