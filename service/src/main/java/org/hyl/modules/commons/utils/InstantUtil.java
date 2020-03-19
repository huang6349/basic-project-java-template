package org.hyl.modules.commons.utils;

import org.hyl.config.GlobalConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface InstantUtil {

    static String format(Instant instant) {
        if (instant != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return localDateTime.format(DateTimeFormatter.ofPattern(GlobalConstants.DATE_TIME_FORMATTER));
        }
        return null;
    }
}
