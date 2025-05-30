package org.myframework.extra.dict;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DictHandler implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        val packageName = StrUtil.removeSuffix(ClassUtil.getPackage(getClass()), ".core.dict");
        ClassUtil.scanPackageByAnnotation(packageName, Dict.class).stream()
                .filter(clazz -> clazz.isEnum() && EnumDict.class.isAssignableFrom(clazz))
                .map(DictUtil::parse)
                .forEach(DictCache::add);
    }
}
