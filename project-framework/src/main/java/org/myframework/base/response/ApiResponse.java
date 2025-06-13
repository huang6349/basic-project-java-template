package org.myframework.base.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

import static org.myframework.base.response.ShowType.ERROR_MESSAGE;
import static org.myframework.base.response.ShowType.WARN_MESSAGE;

@Data
@Builder
public class ApiResponse<T> implements Serializable {

    @Schema(description = "响应状态")
    private Boolean success;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "响应信息")
    private String message;

    @Schema(description = "响应代码")
    private Integer code;

    @Schema(description = "响应方式")
    private Integer showType;

    @Schema(description = "异常描述")
    private String e;

    @Schema(description = "异常编号")
    private String traceId;

    @Schema(description = "主机地址")
    private String host;

    public static <T> ApiResponse<T> fail(String message,
                                          Integer code,
                                          String e) {
        return ApiResponse.<T>builder()
                .success(Boolean.FALSE)
                .message(message)
                .code(code)
                .showType(ERROR_MESSAGE.getShowType())
                .e(e)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message,
                                          Integer code,
                                          String e,
                                          Integer showType) {
        return ApiResponse.<T>builder()
                .success(Boolean.FALSE)
                .message(message)
                .code(code)
                .showType(showType)
                .e(e)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message,
                                          Integer code,
                                          String e,
                                          String traceId,
                                          String host) {
        return ApiResponse.<T>builder()
                .success(Boolean.FALSE)
                .message(message)
                .code(code)
                .showType(ERROR_MESSAGE.getShowType())
                .e(e)
                .traceId(traceId)
                .host(host)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message,
                                          Integer code,
                                          String e,
                                          Integer showType,
                                          String traceId,
                                          String host) {
        return ApiResponse.<T>builder()
                .success(Boolean.FALSE)
                .message(message)
                .code(code)
                .showType(showType)
                .e(e)
                .traceId(traceId)
                .host(host)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .success(Boolean.TRUE)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data,
                                        String message) {
        return ApiResponse.<T>builder()
                .success(Boolean.TRUE)
                .data(data)
                .message(message)
                .showType(WARN_MESSAGE.getShowType())
                .build();
    }

    public static <T> ApiResponse<T> ok(T data,
                                        String message,
                                        Integer showType) {
        return ApiResponse.<T>builder()
                .success(Boolean.TRUE)
                .data(data)
                .message(message)
                .showType(showType)
                .build();
    }
}
