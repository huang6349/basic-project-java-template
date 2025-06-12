package org.myframework.extra.dict;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static cn.hutool.core.util.ClassUtil.scanPackageByAnnotation;

@Component
public class DictHandler implements InitializingBean {

    private static final String EMPTY = "";

    @Override
    public void afterPropertiesSet() {
        scanPackageByAnnotation(EMPTY, Dict.class).stream()
                .filter(clazz -> clazz.isEnum() && EnumDict.class.isAssignableFrom(clazz))
                .map(DictUtil::parse)
                .forEach(DictCache::add);
    }
}
