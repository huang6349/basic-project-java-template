package org.myframework.core.util;

import cn.hutool.core.util.ObjectUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface ReactorUtil {

    static <T> Flux<T> toFlux(Supplier<Iterable<? extends T>> supplier) {
        return Mono.fromSupplier(supplier)
                .flatMapMany(iterable -> ObjectUtil.isNotNull(iterable)
                        ? Flux.fromIterable(iterable)
                        : Flux.empty());
    }

    @SuppressWarnings("UnusedReturnValue")
    static <T> T runBlock(Mono<T> mono) {
        return mono.block();
    }
}
