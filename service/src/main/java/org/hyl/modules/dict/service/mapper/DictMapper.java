package org.hyl.modules.dict.service.mapper;

import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.domain.vm.DictVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DictMapper {

    public List<DictVM> adapt(List<Dict> dicts) {
        return dicts.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public DictVM adapt(Dict dict) {
        return DictVM.adapt(dict);
    }
}
