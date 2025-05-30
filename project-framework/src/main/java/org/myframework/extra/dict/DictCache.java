package org.myframework.extra.dict;

import cn.hutool.core.lang.Opt;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class DictCache {

    private static final Cache<String, DictDefine> cache = Caffeine.newBuilder().build();

    public static DictDefine query(String id) {
        return Opt.ofBlankAble(id)
                .map(cache::getIfPresent)
                .get();
    }

    public static void add(DictDefine payload) {
        Opt.ofNullable(payload)
                .map(DictDefine::getCategory)
                .ifPresent(category -> cache
                        .put(category, payload));
    }
}
