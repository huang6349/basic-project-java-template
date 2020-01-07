package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.hyl.data.auditing.DefaultLevelUtil;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.system.commons.pagination.PaginationUtil;
import org.hyl.system.commons.result.Message;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.hyl.system.domain.Dict;
import org.hyl.system.errors.DataNotAlreadyIDException;
import org.hyl.system.repository.DictRepository;
import org.hyl.system.service.DictService;
import org.hyl.system.web.rest.vm.DictLevelVM;
import org.hyl.system.web.rest.vm.DictVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DictResource {

    private LevelUtil<DictLevelVM> levelUtil = new DefaultLevelUtil<>();

    private final DictRepository dictRepository;

    private final DictService dictService;

    @Autowired
    public DictResource(DictRepository dictRepository, DictService dictService) {
        this.dictRepository = dictRepository;
        this.dictService = dictService;
    }

    @PostMapping("/dict")
    public Message create(@Valid @RequestBody DictVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, dictService.create(vm));
    }

    @GetMapping("/dict")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, dictRepository.findAll().stream().map(DictVM::adapt).collect(Collectors.toList()));
    }

    @GetMapping("/dict/{id}")
    public Message query(@PathVariable Long id) {
        Optional<Dict> optional = dictRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(DictVM::adapt).orElse(null));
    }

    @GetMapping("/dict/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return PaginationUtil.execute(dictRepository.findAll(pageable).map(DictVM::adapt));
    }

    @GetMapping("/dict/tree")
    public Message queryToTree(String name) {
        Specification<Dict> specification = Specifications.<Dict>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return RESTful.success(RestTypeEnum.GET, levelUtil.listToTree(dictRepository.findAll(specification).stream().map(DictLevelVM::adapt).collect(Collectors.toList())));
    }

    @PutMapping("/dict")
    public Message update(@Valid @RequestBody DictVM vm) {
        return RESTful.success(RestTypeEnum.PUT, dictService.update(vm));
    }

    @DeleteMapping("/dict/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, dictService.delete(id));
    }
}
