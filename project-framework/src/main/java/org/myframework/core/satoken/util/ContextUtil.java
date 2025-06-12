package org.myframework.core.satoken.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ContextUtil {

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    private ContextUtil() {
    }

    public static void set(String key, Object value) {
        Map<String, String> map = getLocalMap();
        map.put(key, value == null ? StrUtil.EMPTY : value.toString());
    }

    public static <T> T get(String key, Class<T> type, Object def) {
        Map<String, String> map = getLocalMap();
        return Convert.convert(type, map.getOrDefault(key, String.valueOf(def == null ? StrUtil.EMPTY : def)));
    }

    public static Map<String, String> getLocalMap() {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            map = new ConcurrentHashMap<>(10);
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    public static void remove() {
        StaticLog.debug("清除线程变量中存储的信息");
        THREAD_LOCAL.remove();
    }

    /****************** 登录信息 ***************/

    public static void setLoginId(Long loginId) {
        StaticLog.debug("设置线程变量中的登录信息");
        set("loginId", loginId);
    }

    public static Long getLoginId() {
        StaticLog.debug("获取线程变量中的登录信息");
        return get("loginId", Long.class, 0L);
    }
}
