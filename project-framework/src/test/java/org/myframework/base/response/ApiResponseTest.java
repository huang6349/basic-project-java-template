package org.myframework.base.response;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.hutool.core.util.RandomUtil.randomStringUpper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.myframework.base.response.ShowType.*;
import static org.myframework.core.exception.ErrorCode.ERR_BUSINESS;

@TestMethodOrder(OrderAnnotation.class)
class ApiResponseTest {

    static final String DATA = randomStringUpper(18);

    static final String MESSAGE = randomStringUpper(18);

    static final Integer CODE = ERR_BUSINESS.getCode();

    static final String E = randomStringUpper(18);

    static final String TRACE_ID = IdUtil.randomUUID();

    static final String HOST = randomStringUpper(18);

    @Order(0)
    @Test
    void okDef1() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .defExec(Boolean.TRUE)
                        .build(),
                ApiResponse.okDef(DATA));
    }

    @Order(1)
    @Test
    void okDef2() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(WARN_MESSAGE)
                        .defExec(Boolean.TRUE)
                        .build(),
                ApiResponse.okDef(DATA,
                        MESSAGE));
    }

    @Order(2)
    @Test
    void okDef3() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(ERROR_MESSAGE)
                        .defExec(Boolean.TRUE)
                        .build(),
                ApiResponse.okDef(DATA,
                        MESSAGE,
                        ERROR_MESSAGE));
    }

    @Order(3)
    @Test
    void ok1() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.ok(DATA));
    }

    @Order(4)
    @Test
    void ok2() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(WARN_MESSAGE)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.ok(DATA,
                        MESSAGE));
    }

    @Order(5)
    @Test
    void ok3() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(ERROR_MESSAGE)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.ok(DATA,
                        MESSAGE,
                        ERROR_MESSAGE));
    }

    @Order(6)
    @Test
    void fail1() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(ERROR_MESSAGE)
                        .e(E)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E));
    }

    @Order(7)
    @Test
    void fail2() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(NOTIFICATION)
                        .e(E)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        NOTIFICATION));
    }

    @Order(8)
    @Test
    void fail3() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(ERROR_MESSAGE)
                        .e(E)
                        .traceId(TRACE_ID)
                        .host(HOST)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        TRACE_ID,
                        HOST));
    }

    @Order(9)
    @Test
    void fail4() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(NOTIFICATION)
                        .e(E)
                        .traceId(TRACE_ID)
                        .host(HOST)
                        .defExec(Boolean.FALSE)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        NOTIFICATION,
                        TRACE_ID,
                        HOST));
    }

    void compare(ApiResponse<String> a, ApiResponse<String> b) {
        assertThat(a.getSuccess()).isEqualTo(b.getSuccess());
        assertThat(a.getData()).isEqualTo(b.getData());
        assertThat(a.getMessage()).isEqualTo(b.getMessage());
        assertThat(a.getCode()).isEqualTo(b.getCode());
        assertThat(a.getShowType()).isEqualTo(b.getShowType());
        assertThat(a.getE()).isEqualTo(b.getE());
        assertThat(a.getTraceId()).isEqualTo(b.getTraceId());
        assertThat(a.getHost()).isEqualTo(b.getHost());
        assertThat(a.getDefExec()).isEqualTo(b.getDefExec());
    }
}
