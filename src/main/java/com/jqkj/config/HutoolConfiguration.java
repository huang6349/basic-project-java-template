package com.jqkj.config;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SpringUtil.class)
public class HutoolConfiguration {
}
