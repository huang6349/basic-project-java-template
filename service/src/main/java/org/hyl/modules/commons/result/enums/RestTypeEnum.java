package org.hyl.modules.commons.result.enums;

public enum RestTypeEnum {
    DEFAULT(0), POST(1), DELETE(2), GET(3), PUT(4);

    private final Integer type;

    RestTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
