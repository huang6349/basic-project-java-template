package org.myframework.core.satoken;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public final class ContextUtil {

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    private ContextUtil() {
    }

    public static void remove() {
        log.debug("将登录信息从线程变量中清除");
        THREAD_LOCAL.remove();
    }
}
