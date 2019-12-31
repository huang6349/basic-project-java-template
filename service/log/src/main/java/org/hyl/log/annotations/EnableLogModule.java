package org.hyl.log.annotations;

import org.hyl.log.config.LogConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LogConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableLogModule {
}
