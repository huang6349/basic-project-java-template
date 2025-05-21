package org.myframework.es.service;

import cn.hutool.core.lang.Opt;
import com.mybatisflex.core.util.SqlUtil;
import lombok.val;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.kernel.BaseEsMapper;
import org.dromara.easyes.core.kernel.Wrapper;
import org.myframework.core.util.ReactorUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@SuppressWarnings({"CodeBlock2Expr", "unused"})
public interface EsService<Entity> {

    int DEFAULT_PAGE_NUMBER = 1;

    int DEFAULT_PAGE_SIZE = 10;

    int MAX_TOTAL = 10000;

    BaseEsMapper<Entity> getMapper();

    // ===== 保存（增）操作 =====

    default Mono<Boolean> save(Entity entity) {
        return Mono.fromSupplier(() -> {
            val result = getMapper()
                    .insert(entity);
            return SqlUtil.toBool(result);
        });
    }

    // ===== 保存（增）操作 =====

    default Mono<Entity> getById(Serializable id) {
        return Mono.fromSupplier(() -> {
            return getMapper()
                    .selectById(id);
        });
    }

    default Mono<Entity> getOne(Wrapper<Entity> query) {
        return Mono.fromSupplier(() -> {
            return getMapper()
                    .selectOne(query);
        });
    }

    default Flux<Entity> list(Wrapper<Entity> query) {
        return ReactorUtil.toFlux(() -> {
            return getMapper()
                    .selectList(query);
        });
    }

    default Mono<Long> count(Wrapper<Entity> query) {
        return Mono.fromSupplier(() -> {
            return getMapper()
                    .selectCount(query);
        });
    }

    default Mono<Long> count() {
        return Mono.fromSupplier(() -> {
            return getMapper()
                    .selectCount(query());
        });
    }

    // ===== 分页查询操作 =====

    default Mono<EsPageInfo<Entity>> page(Integer pageNumber,
                                          Integer pageSize,
                                          Wrapper<Entity> query) {
        return Mono.fromSupplier(() -> {
            val number = Opt.ofNullable(pageNumber)
                    .orElse(DEFAULT_PAGE_NUMBER);
            val size = Opt.ofNullable(pageSize)
                    .orElse(DEFAULT_PAGE_SIZE);
            val offset = (number - 1) * size;
            if (offset + size > MAX_TOTAL)
                throw new RuntimeException("分页查询不能超过" + MAX_TOTAL + "条记录");
            return getMapper()
                    .pageQuery(query, number, size);
        });
    }

    // ===== 查询包装器操作 =====

    default LambdaEsQueryWrapper<Entity> query() {
        return new LambdaEsQueryWrapper<>();
    }
}
