package com.docker.core.di.module.cachemodule;

import com.docker.core.base.BaseApplication;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CacheModule_Factory implements Factory<CacheModule> {
  private final Provider<BaseApplication> applicationProvider;

  public CacheModule_Factory(Provider<BaseApplication> applicationProvider) {
    assert applicationProvider != null;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public CacheModule get() {
    return new CacheModule(applicationProvider.get());
  }

  public static Factory<CacheModule> create(Provider<BaseApplication> applicationProvider) {
    return new CacheModule_Factory(applicationProvider);
  }
}
