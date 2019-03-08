package com.docker.corepro.di;


import com.docker.core.di.component.BaseActivityComponent;
import com.docker.core.di.scope.ActivityScope;
import com.docker.corepro.ui.AccountActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by zhangxindang on 2018/11/21.
 */

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class UIMoudle {

//    @ActivityScope
//    @ContributesAndroidInjector/*(modules = HomeActivityModule.class)*/
//    abstract MainActivity contributeMainActivitytInjector();

    @ActivityScope
    @ContributesAndroidInjector
    abstract AccountActivity contributeAccountActivityInjector();

}
