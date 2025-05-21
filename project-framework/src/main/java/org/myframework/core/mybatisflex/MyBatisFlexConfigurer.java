package org.myframework.core.mybatisflex;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;

public abstract class MyBatisFlexConfigurer implements MyBatisFlexCustomizer {

    @Override
    public void customize(FlexGlobalConfig globalConfig) {
        AuditManager.setAuditEnable(Boolean.TRUE);
        globalConfig.setPrintBanner(Boolean.FALSE);
    }
}
