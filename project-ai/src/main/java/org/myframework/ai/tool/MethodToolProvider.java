package org.myframework.ai.tool;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.log.StaticLog;
import com.agentsflex.core.llm.functions.Function;
import com.agentsflex.core.llm.functions.JavaNativeFunction;
import com.agentsflex.core.llm.functions.annotation.FunctionDef;
import lombok.val;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MethodToolProvider implements ToolProvider {

    private final List<Function> tools = new ArrayList<>();

    public MethodToolProvider(Object object) {
        val clazz = object.getClass();
        // 添加带注释的工具
        for (Method method : ClassUtil.getPublicMethods(clazz)) {
            if (method.isAnnotationPresent(FunctionDef.class)) {
                val function = new JavaNativeFunction();
                function.setClazz(clazz);
                function.setMethod(method);
                function.setObject(object);
                tools.add(function);
            }
        }
        // 添加自己就是工具的工具
        if (ClassUtil.isAssignable(ToolProvider.class, clazz)) {
            tools.addAll(((ToolProvider) object).getTools());
        }
    }

    @Override
    public Collection<Function> getTools() {
        StaticLog.debug("获取工具: {}", tools);
        return tools;
    }
}
