package com.bfhd.bfsourcelibary.command;

import com.docker.common.widget.EmptyCommand;
import com.scwang.smartrefresh.layout.binding.command.ReplyCommand;

public class BaseCommand {

    public EmptyCommand retrycommand;

    public ReplyCommand onrefreshCommand;

    public ReplyCommand onloadmoreCommand;

    public void OnRetryLoad(EmptyCommand retrycommand) {
        this.retrycommand = retrycommand;
    }

    public void OnRefresh(ReplyCommand onrefreshCommand) {
        this.onrefreshCommand = onrefreshCommand;
    }

    public void OnLoadMore(ReplyCommand onloadmoreCommand) {

        this.onloadmoreCommand = onloadmoreCommand;

    }
}
