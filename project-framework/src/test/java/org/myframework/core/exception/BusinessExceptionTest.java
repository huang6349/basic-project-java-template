package org.myframework.core.exception;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static cn.hutool.core.util.RandomUtil.randomStringUpper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.myframework.base.response.ShowType.ERROR_MESSAGE;
import static org.myframework.base.response.ShowType.NOTIFICATION;
import static org.myframework.core.exception.ErrorCode.ERROR;
import static org.myframework.core.exception.ErrorCode.ERR_BUSINESS;

@TestMethodOrder(OrderAnnotation.class)
class BusinessExceptionTest {

    static final String TRACE_ID = IdUtil.randomUUID();

    static final String HOST = randomStringUpper(18);

    @Order(0)
    @Test
    void test1() {
        var exception = new BusinessException();
        assertEquals(exception.getMessage(), ERR_BUSINESS.getMessage());
        assertEquals(exception.getErrorCode(), ERR_BUSINESS.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(1)
    @Test
    void test2() {
        var exception = new BusinessException(ERROR.getMessage());
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERR_BUSINESS.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(2)
    @Test
    void test3() {
        var exception = new BusinessException(ERROR);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(3)
    @Test
    void test4() {
        var exception = new BusinessException(ERROR.getCode(), ERROR.getMessage());
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(4)
    @Test
    void test5() {
        var exception = new BusinessException("{}", ERROR.getMessage());
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERR_BUSINESS.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(5)
    @Test
    void test6() {
        var exception = new BusinessException(ERROR, NOTIFICATION);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), NOTIFICATION);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(6)
    @Test
    void test7() {
        var exception = new BusinessException(ERROR.getCode(), ERROR.getMessage(), NOTIFICATION);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), NOTIFICATION);
        assertNull(exception.getTraceId());
        assertNull(exception.getHost());
    }

    @Order(7)
    @Test
    void test8() {
        var exception = new BusinessException(ERROR.getMessage(), TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERR_BUSINESS.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }

    @Order(8)
    @Test
    void test9() {
        var exception = new BusinessException(ERROR, TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }

    @Order(9)
    @Test
    void test10() {
        var exception = new BusinessException(ERROR.getCode(), ERROR.getMessage(), TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), ERROR_MESSAGE);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }

    @Order(10)
    @Test
    void test11() {
        var exception = new BusinessException(ERROR.getMessage(), NOTIFICATION, TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERR_BUSINESS.getCode());
        assertEquals(exception.getShowType(), NOTIFICATION);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }

    @Order(11)
    @Test
    void test12() {
        var exception = new BusinessException(ERROR, NOTIFICATION, TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), NOTIFICATION);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }

    @Order(12)
    @Test
    void test13() {
        var exception = new BusinessException(ERROR.getCode(), ERROR.getMessage(), NOTIFICATION, TRACE_ID, HOST);
        assertEquals(exception.getMessage(), ERROR.getMessage());
        assertEquals(exception.getErrorCode(), ERROR.getCode());
        assertEquals(exception.getShowType(), NOTIFICATION);
        assertEquals(exception.getTraceId(), TRACE_ID);
        assertEquals(exception.getHost(), HOST);
    }
}
