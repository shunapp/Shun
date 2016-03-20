package com.shun.app.domain;

import com.shun.app.domain.catalog.CatalogManager;
import com.shun.app.domain.catalog.CatalogModule;
import dagger.Component;

@DomainScope
@Component(
    modules = {
        CatalogModule.class,
    })
public interface DomainComponent {
  CatalogManager catalogManager();
}
