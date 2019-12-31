package org.hyl.commons.result.enums;

public enum NetworkEnum {
    SUCCESS(true), ERROR(false);

    private final boolean success;

    NetworkEnum(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
