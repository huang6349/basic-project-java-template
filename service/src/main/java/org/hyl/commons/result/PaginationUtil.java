package org.hyl.commons.result;

import com.google.common.collect.Maps;
import org.hyl.commons.result.enums.rest.RestTypeEnum;
import org.hyl.commons.result.rest.RESTful;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface PaginationUtil {

    static Message execute(Page page) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("page", page.getNumber());
        map.put("size", page.getSize());
        map.put("total", page.getTotalElements());
        return RESTful.success(RestTypeEnum.GET, page.getContent(), map);
    }
}
