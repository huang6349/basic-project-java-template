package org.huangyalong;

import org.huangyalong.core.AbstractIntegration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class Application extends AbstractIntegration {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .applicationStartup(new BufferingApplicationStartup(20480))
                .run(args);
    }
}
