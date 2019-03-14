package com.scwang.smartrefresh.layout.binding.command;


/**
 * Created by kelin on 15-8-4.
 */

@FunctionalInterface
public interface ReplyCommandParam<T> {

    void exectue(T t);


}
