package org.myframework.extra.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Style {
    DEFAULT(0, "默认(default)"),
    PRIMARY(1, "主要(primary)"),
    SUCCESS(2, "成功(success)"),
    ERROR(3, "失败(error)"),
    PROCESSING(4, "进行(processing)"),
    WARNING(5, "警告(warning)");

    private final Integer value;

    private final String label;
}
