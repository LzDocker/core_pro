package com.docker.core.di.module.httpmodule.progress;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ProgressManager_Factory implements Factory<ProgressManager> {
  private final MembersInjector<ProgressManager> progressManagerMembersInjector;

  public ProgressManager_Factory(MembersInjector<ProgressManager> progressManagerMembersInjector) {
    assert progressManagerMembersInjector != null;
    this.progressManagerMembersInjector = progressManagerMembersInjector;
  }

  @Override
  public ProgressManager get() {
    return MembersInjectors.injectMembers(progressManagerMembersInjector, new ProgressManager());
  }

  public static Factory<ProgressManager> create(
      MembersInjector<ProgressManager> progressManagerMembersInjector) {
    return new ProgressManager_Factory(progressManagerMembersInjector);
  }
}
