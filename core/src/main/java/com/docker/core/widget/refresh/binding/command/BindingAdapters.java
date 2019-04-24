package com.docker.core.widget.refresh.binding.command;

import android.databinding.BindingAdapter;

import com.docker.core.widget.refresh.SmartRefreshLayout;
import com.docker.core.widget.refresh.api.RefreshLayout;
import com.docker.core.widget.refresh.listener.OnLoadMoreListener;
import com.docker.core.widget.refresh.listener.OnRefreshListener;


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
