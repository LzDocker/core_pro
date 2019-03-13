package com.docker.commonwidget.binding.command;

import android.databinding.BindingAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


/**
 *  SmartRefreshLayout
 */
public class BindingAdapters {
    @BindingAdapter({"onRefreshCommand"})
    public static void setonRefreshCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommand onRefreshCommand) {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                onRefreshCommand.exectue();
            }
        });
    }

    @BindingAdapter({"onloadmoreCommand"})
    public static void setonloadmoreCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommand onloadmoreCommand) {
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                onloadmoreCommand.exectue();
            }
        });
    }

    @BindingAdapter({"oncompleteCommand"})
    public static void setoncompleteCommand(SmartRefreshLayout smartRefreshLayout, final ReplyCommandParam oncompleteCommand) {
        oncompleteCommand.exectue(smartRefreshLayout);
    }


}
