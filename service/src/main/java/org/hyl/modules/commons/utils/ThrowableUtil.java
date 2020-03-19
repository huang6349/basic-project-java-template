package org.hyl.modules.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface ThrowableUtil {

    static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
