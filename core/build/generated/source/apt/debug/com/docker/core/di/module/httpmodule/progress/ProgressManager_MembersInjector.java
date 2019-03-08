package com.docker.core.di.module.httpmodule.progress;

import com.docker.core.di.module.httpmodule.MHeader;
import com.docker.core.util.AppExecutors;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ProgressManager_MembersInjector implements MembersInjector<ProgressManager> {
  private final Provider<AppExecutors> appExecutorsProvider;

  private final Provider<MHeader> mHeaderProvider;

  public ProgressManager_MembersInjector(
      Provider<AppExecutors> appExecutorsProvider, Provider<MHeader> mHeaderProvider) {
    assert appExecutorsProvider != null;
    this.appExecutorsProvider = appExecutorsProvider;
    assert mHeaderProvider != null;
    this.mHeaderProvider = mHeaderProvider;
  }

  public static MembersInjector<ProgressManager> create(
      Provider<AppExecutors> appExecutorsProvider, Provider<MHeader> mHeaderProvider) {
    return new ProgressManager_MembersInjector(appExecutorsProvider, mHeaderProvider);
  }

  @Override
  public void injectMembers(ProgressManager instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.appExecutors = appExecutorsProvider.get();
    instance.mHeader = mHeaderProvider.get();
  }

  public static void injectAppExecutors(
      ProgressManager instance, Provider<AppExecutors> appExecutorsProvider) {
    instance.appExecutors = appExecutorsProvider.get();
  }

  public static void injectMHeader(ProgressManager instance, Provider<MHeader> mHeaderProvider) {
    instance.mHeader = mHeaderProvider.get();
  }
}
