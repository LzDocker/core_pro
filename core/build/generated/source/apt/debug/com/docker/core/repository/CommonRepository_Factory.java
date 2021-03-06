// Generated by dagger.internal.codegen.ComponentProcessor (https://google.github.io/dagger).
package com.docker.core.repository;

import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.util.AppExecutors;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CommonRepository_Factory implements Factory<CommonRepository> {
  private final Provider<AppExecutors> appExecutorsProvider;

  private final Provider<CacheDatabase> cacheDatabaseProvider;

  public CommonRepository_Factory(
      Provider<AppExecutors> appExecutorsProvider, Provider<CacheDatabase> cacheDatabaseProvider) {
    assert appExecutorsProvider != null;
    this.appExecutorsProvider = appExecutorsProvider;
    assert cacheDatabaseProvider != null;
    this.cacheDatabaseProvider = cacheDatabaseProvider;
  }

  @Override
  public CommonRepository get() {
    return new CommonRepository(appExecutorsProvider.get(), cacheDatabaseProvider.get());
  }

  public static Factory<CommonRepository> create(
      Provider<AppExecutors> appExecutorsProvider, Provider<CacheDatabase> cacheDatabaseProvider) {
    return new CommonRepository_Factory(appExecutorsProvider, cacheDatabaseProvider);
  }
}
