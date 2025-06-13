package org.myframework.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShowType {
    SILENT(0),
    WARN_MESSAGE(1),
    ERROR_MESSAGE(2),
    NOTIFICATION(4),
    REDIRECT(9);

    private final Integer showType;
}
