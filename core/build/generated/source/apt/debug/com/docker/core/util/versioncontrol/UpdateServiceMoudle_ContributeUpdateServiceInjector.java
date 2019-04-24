package com.docker.core.util.versioncontrol;

import android.app.Service;
import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.ServiceKey;
import dagger.multibindings.IntoMap;

@Module(
  subcomponents =
      UpdateServiceMoudle_ContributeUpdateServiceInjector.UpdateServiceSubcomponent.class
)
public abstract class UpdateServiceMoudle_ContributeUpdateServiceInjector {
  private UpdateServiceMoudle_ContributeUpdateServiceInjector() {}

  @Binds
  @IntoMap
  @ServiceKey(UpdateService.class)
  abstract AndroidInjector.Factory<? extends Service> bindAndroidInjectorFactory(
      UpdateServiceSubcomponent.Builder builder);

  @Subcomponent
  public interface UpdateServiceSubcomponent extends AndroidInjector<UpdateService> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<UpdateService> {}
  }
}
