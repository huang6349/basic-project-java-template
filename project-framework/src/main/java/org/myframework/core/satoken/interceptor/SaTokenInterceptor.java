package org.myframework.core.satoken.interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.myframework.core.satoken.ContextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SaTokenInterceptor extends SaInterceptor {

    @SuppressWarnings("all")
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        // 清空ThreadLocal的值，防止下次请求时获取到的值是旧数据，同时也能防止内存溢出
        ContextUtil.remove();
    }
}
