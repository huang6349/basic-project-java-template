package org.hyl.file.annotations;

import org.hyl.file.ModuleConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ModuleConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableFileModule {
}
