package org.hyl.data.annotations;

import org.hyl.data.config.DataConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DataConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableDataModule {
}
