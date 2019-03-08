package com.docker.core.di.module.httpmodule;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.CookieJar;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HttpClientModule_ProviderCookieJarFactory implements Factory<CookieJar> {
  private final HttpClientModule module;

  public HttpClientModule_ProviderCookieJarFactory(HttpClientModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public CookieJar get() {
    return Preconditions.checkNotNull(
        module.providerCookieJar(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CookieJar> create(HttpClientModule module) {
    return new HttpClientModule_ProviderCookieJarFactory(module);
  }

  /** Proxies {@link HttpClientModule#providerCookieJar()}. */
  public static CookieJar proxyProviderCookieJar(HttpClientModule instance) {
    return instance.providerCookieJar();
  }
}
