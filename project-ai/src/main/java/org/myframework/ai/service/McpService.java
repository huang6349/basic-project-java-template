package org.myframework.ai.service;

import cn.hutool.extra.spring.SpringUtil;
import lombok.val;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.InitializingBean;

import static cn.hutool.core.text.CharSequenceUtil.addSuffixIfNot;
import static cn.hutool.core.text.CharSequenceUtil.removeSufAndLowerFirst;
import static cn.hutool.core.util.ClassUtil.getClassName;

@SuppressWarnings("unused")
public abstract class McpService implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        val className = getClassName(this, Boolean.TRUE);
        val name = removeSufAndLowerFirst(className, "Service");
        SpringUtil.registerBean(addSuffixIfNot(name, "Tools"),
                MethodToolCallbackProvider.builder()
                        .toolObjects(this)
                        .build());
    }
}
