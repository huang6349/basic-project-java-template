package org.myframework.core.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.StaticLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface ReactorUtil {

    static <T> Flux<T> toFlux(Supplier<Iterable<? extends T>> supplier) {
        StaticLog.trace("异步发送");
        return Mono.fromSupplier(supplier)
                .flatMapMany(iterable -> ObjectUtil.isNotNull(iterable)
                        ? Flux.fromIterable(iterable)
                        : Flux.empty());
    }

    static <T> T runBlock(Mono<T> mono) {
        StaticLog.trace("同步接收");
        return mono.block();
    }
}
