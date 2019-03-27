package com.docker.core.di.module.httpmodule;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.annotation.Generated;
import okhttp3.Interceptor;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GlobalConfigModule_ProvideInterceptorsFactory
    implements Factory<List<Interceptor>> {
  private final GlobalConfigModule module;

  public GlobalConfigModule_ProvideInterceptorsFactory(GlobalConfigModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public List<Interceptor> get() {
    return Preconditions.checkNotNull(
        module.provideInterceptors(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<List<Interceptor>> create(GlobalConfigModule module) {
    return new GlobalConfigModule_ProvideInterceptorsFactory(module);
  }

  /** Proxies {@link GlobalConfigModule#provideInterceptors()}. */
  public static List<Interceptor> proxyProvideInterceptors(GlobalConfigModule instance) {
    return instance.provideInterceptors();
  }
}
