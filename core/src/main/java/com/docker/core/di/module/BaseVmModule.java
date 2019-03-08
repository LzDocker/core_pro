package com.docker.core.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.docker.core.viewmodel.HivescmViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BaseVmModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(HivescmViewModelFactory factory);

}
