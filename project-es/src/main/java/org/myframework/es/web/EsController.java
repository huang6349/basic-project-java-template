package org.myframework.es.web;

import lombok.Getter;
import org.myframework.es.service.EsService;

import javax.annotation.Resource;

@SuppressWarnings("LombokGetterMayBeUsed")
public abstract class EsController<S extends EsService<Entity>, Entity> {

    @Resource
    @Getter
    protected S service;
}
