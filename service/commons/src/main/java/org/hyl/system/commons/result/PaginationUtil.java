package org.hyl.system.commons.result;

import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.springframework.data.domain.Page;

public interface PaginationUtil {

    static Message execute(Page page) {
        PaginationParams params = new PaginationParams();
        params.setPage(page.getNumber());
        params.setSize(page.getSize());
        params.setTotal(page.getTotalElements());
        return RESTful.success(RestTypeEnum.GET, page.getContent(), params);
    }
}
