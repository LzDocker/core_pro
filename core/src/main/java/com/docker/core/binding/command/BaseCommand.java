package com.docker.core.binding.command;

import com.docker.core.widget.emptylayout.EmptyCommand;
import com.docker.core.widget.refresh.binding.command.ReplyCommand;

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
