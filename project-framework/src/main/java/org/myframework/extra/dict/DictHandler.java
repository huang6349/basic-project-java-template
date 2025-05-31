package org.myframework.extra.dict;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static cn.hutool.core.util.ClassUtil.scanPackageByAnnotation;

@AllArgsConstructor
@Slf4j
@Component
public class DictHandler implements InitializingBean {

    static final String EMPTY = "";

    @Override
    public void afterPropertiesSet() {
        scanPackageByAnnotation(EMPTY, Dict.class).stream()
                .filter(clazz -> clazz.isEnum() && EnumDict.class.isAssignableFrom(clazz))
                .map(DictUtil::parse)
                .forEach(DictCache::add);
    }
}
