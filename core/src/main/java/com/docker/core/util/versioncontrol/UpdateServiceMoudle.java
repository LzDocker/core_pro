package com.docker.core.util.versioncontrol;


import com.docker.core.di.component.BaseServiceComponent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by zhangxindang on 2018/11/21.
 */

@Module(subcomponents = {BaseServiceComponent.class})
public abstract class UpdateServiceMoudle {

    @ContributesAndroidInjector
    abstract UpdateService contributeUpdateServiceInjector();

}
