package org.myframework.base.response;

import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonValue
    private final Integer value;
}
