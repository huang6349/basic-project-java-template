package org.myframework.base.response;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.hutool.core.util.RandomUtil.randomStringUpper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.myframework.base.response.ShowType.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@TestMethodOrder(OrderAnnotation.class)
class ApiResponseTest {

    static final String DATA = randomStringUpper(18);

    static final String MESSAGE = randomStringUpper(18);

    static final Integer CODE = INTERNAL_SERVER_ERROR.value();

    static final String E = randomStringUpper(18);

    static final String TRACE_ID = IdUtil.randomUUID();

    static final String HOST = randomStringUpper(18);

    @Order(1)
    @Test
    void fail1() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(ERROR_MESSAGE.getShowType())
                        .e(E)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E));
    }

    @Order(2)
    @Test
    void fail2() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(NOTIFICATION.getShowType())
                        .e(E)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        NOTIFICATION.getShowType()));
    }

    @Order(3)
    @Test
    void fail3() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(ERROR_MESSAGE.getShowType())
                        .e(E)
                        .traceId(TRACE_ID)
                        .host(HOST)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        TRACE_ID,
                        HOST));
    }

    @Order(4)
    @Test
    void fail4() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.FALSE)
                        .message(MESSAGE)
                        .code(CODE)
                        .showType(NOTIFICATION.getShowType())
                        .e(E)
                        .traceId(TRACE_ID)
                        .host(HOST)
                        .build(),
                ApiResponse.fail(MESSAGE,
                        CODE,
                        E,
                        NOTIFICATION.getShowType(),
                        TRACE_ID,
                        HOST));
    }

    @Order(5)
    @Test
    void ok1() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .build(),
                ApiResponse.ok(DATA));
    }

    @Order(6)
    @Test
    void ok2() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(WARN_MESSAGE.getShowType())
                        .build(),
                ApiResponse.ok(DATA,
                        MESSAGE));
    }

    @Order(7)
    @Test
    void ok3() {
        compare(ApiResponse.<String>builder()
                        .success(Boolean.TRUE)
                        .data(DATA)
                        .message(MESSAGE)
                        .showType(ERROR_MESSAGE.getShowType())
                        .build(),
                ApiResponse.ok(DATA,
                        MESSAGE,
                        ERROR_MESSAGE.getShowType()));
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
    }
}
