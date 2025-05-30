package org.myframework.extra.dict;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsDefault {
    YES(0, "缺省"),
    NO(1, "其他");

    private final Integer value;

    private final String label;
}
