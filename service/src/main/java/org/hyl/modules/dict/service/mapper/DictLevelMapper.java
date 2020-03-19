package org.hyl.modules.dict.service.mapper;

import org.hyl.modules.data.auditing.utils.LevelUtil;
import org.hyl.modules.data.auditing.utils.impl.DefaultLevelUtil;
import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.domain.vm.DictLevelVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DictLevelMapper {

    private LevelUtil<DictLevelVM> levelUtil = new DefaultLevelUtil<>();

    public List<DictLevelVM> adapt(List<Dict> dicts) {
        return levelUtil.listToTree(dicts.stream().filter(Objects::nonNull).map(DictLevelVM::adapt).collect(Collectors.toList()));
    }
}
