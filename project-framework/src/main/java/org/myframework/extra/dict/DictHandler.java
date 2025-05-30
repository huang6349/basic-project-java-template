package org.myframework.extra.dict;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import static cn.hutool.core.text.CharSequenceUtil.removeSuffix;
import static cn.hutool.core.util.ClassUtil.getPackage;
import static cn.hutool.core.util.ClassUtil.scanPackageByAnnotation;

@AllArgsConstructor
@Slf4j
@Component
public class DictHandler implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        val packageName = removeSuffix(getPackage(getClass()), ".extra.dict");
        scanPackageByAnnotation(packageName, Dict.class).stream()
                .filter(clazz -> clazz.isEnum() && EnumDict.class.isAssignableFrom(clazz))
                .map(DictUtil::parse)
                .forEach(DictCache::add);
    }
}
