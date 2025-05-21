package org.myframework.test.web;

import lombok.Getter;
import org.myframework.es.service.EsService;
import org.myframework.test.MyFrameworkTest;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "LombokGetterMayBeUsed"})
public abstract class EsControllerTest<S extends EsService<Entity>, Entity>
        extends MyFrameworkTest {

    @Autowired
    @Getter
    protected S service;
}
