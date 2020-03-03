package org.hyl.dict.annotations;

import org.hyl.dict.ModuleConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ModuleConfiguration.class})
@ConditionalOnWebApplication
public @interface EnableDictModule {
}
