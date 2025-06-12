package org.myframework.core.mybatisflex;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;

public abstract class MyBatisFlexConfigurer implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        DataSourceKey.setThreadLocal(new TransmittableThreadLocal<>());
        AuditManager.setAuditEnable(Boolean.TRUE);
        globalConfig.setPrintBanner(Boolean.FALSE);
    }
}
