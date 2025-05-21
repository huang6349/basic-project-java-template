package org.huangyalong;

import lombok.extern.slf4j.Slf4j;
import org.huangyalong.core.AbstractIntegration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@Slf4j
@SpringBootApplication
public class Application extends AbstractIntegration {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .applicationStartup(new BufferingApplicationStartup(20480))
                .run(args);
    }
}
