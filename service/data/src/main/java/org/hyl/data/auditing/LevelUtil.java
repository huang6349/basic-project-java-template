package org.hyl.data.auditing;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public interface LevelUtil<T extends AbstractLevelAuditingVM<T>> {

    String SEPARATOR = ".";

    String ROOT = "0";

    String SUFFIX = "E";

    static String calculateLevel() {
        return StringUtils.join(ROOT, SUFFIX);
    }

    static String calculateLevel(String parentLevel, long parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return calculateLevel();
        }
        return StringUtils.join(parentLevel, SEPARATOR, parentId, SUFFIX);
    }

    List<T> listToTree(List<T> list);

    List<T> listToTree(List<T> list, String level);
}
