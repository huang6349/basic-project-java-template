package org.myframework.es.service.impl;

import lombok.Getter;
import org.dromara.easyes.core.kernel.BaseEsMapper;
import org.myframework.es.service.EsService;

import javax.annotation.Resource;

@SuppressWarnings("LombokGetterMayBeUsed")
public abstract class EsServiceImpl<Mapper extends BaseEsMapper<Entity>, Entity>
        implements EsService<Entity> {

    @Resource
    @Getter
    protected Mapper mapper;
}
