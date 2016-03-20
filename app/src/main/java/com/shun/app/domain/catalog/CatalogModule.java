package com.shun.app.domain.catalog;

import com.shun.app.BuildConfig;
import com.shun.app.domain.DomainScope;
import com.uwetrottmann.tmdb.Tmdb;
import dagger.Module;
import dagger.Provides;

@Module
public class CatalogModule {
  @Provides @DomainScope public CatalogManager provideCatalogManager(Tmdb tmdbManager) {
    return new TmdbCatalogManager(tmdbManager);
  }

  @Provides @DomainScope public Tmdb provideTmdbManager() {
    Tmdb manager = new Tmdb();
    manager.setApiKey(BuildConfig.TMDB_API_KEY);

    if (BuildConfig.DEBUG) {
      manager.setIsDebug(true);
    }

    return manager;
  }
}
