package org.hyl.config;

public interface GlobalConstants {

    Byte DATA_NORMAL_STATE = 1;

    Byte DATA_DELETE_STATE = 0;

    Byte DATA_KEEP_STATE = 2;

    Byte DATA_DISABLED_STATE = 3;

    String DATA_NORMAL_STATE_TEXT = "正常";

    String DATA_DELETE_STATE_TEXT = "已删除";

    String DATA_KEEP_STATE_TEXT = "系统保留";

    String DATA_DISABLED_STATE_TEXT = "禁用";

    String DATE_TIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    String SYSTEM_ACCOUNT = "SYSTEM";

    Long DICT_ID_SEX = 10000L;

    Long DICT_ID_METHOD = 10100L;

    String AUTHORITY_ANONYMOUS = "ROLE_ANONYMOUS";
}
