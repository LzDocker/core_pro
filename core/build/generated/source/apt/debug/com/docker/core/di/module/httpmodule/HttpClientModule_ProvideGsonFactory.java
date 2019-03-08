package com.docker.core.di.module.httpmodule;

import com.google.gson.Gson;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HttpClientModule_ProvideGsonFactory implements Factory<Gson> {
  private final HttpClientModule module;

  public HttpClientModule_ProvideGsonFactory(HttpClientModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public Gson get() {
    return Preconditions.checkNotNull(
        module.provideGson(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Gson> create(HttpClientModule module) {
    return new HttpClientModule_ProvideGsonFactory(module);
  }
}
