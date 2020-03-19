package org.hyl.modules.dict.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.modules.commons.exception.BadRequestException;
import org.hyl.modules.commons.exception.DataNotAlreadyIDException;
import org.hyl.modules.commons.result.Message;
import org.hyl.modules.commons.result.PaginationUtil;
import org.hyl.modules.commons.result.RESTful;
import org.hyl.modules.commons.result.enums.RestTypeEnum;
import org.hyl.modules.data.auditing.utils.LevelUtil;
import org.hyl.modules.dict.domain.Dict;
import org.hyl.modules.dict.repository.DictRepository;
import org.hyl.modules.dict.service.DictService;
import org.hyl.modules.dict.domain.vm.DictVM;
import org.hyl.modules.dict.service.mapper.DictLevelMapper;
import org.hyl.modules.dict.service.mapper.DictMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/api")
public class DictResource {

    private final DictRepository dictRepository;

    private final DictService dictService;

    private final DictMapper dictMapper;

    private final DictLevelMapper dictLevelMapper;

    public DictResource(DictRepository dictRepository, DictService dictService, DictMapper dictMapper, DictLevelMapper dictLevelMapper) {
        this.dictRepository = dictRepository;
        this.dictService = dictService;
        this.dictMapper = dictMapper;
        this.dictLevelMapper = dictLevelMapper;
    }

    @ApiOperation("新增一个字典")
    @PostMapping("/dict")
    public Message create(@Valid @RequestBody DictVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, dictService.create(vm));
    }

    @ApiOperation("查询所有的字典")
    @GetMapping("/dict")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, dictMapper.adapt(dictRepository.findAll()));
    }

    @ApiOperation("查询一个字典")
    @GetMapping("/dict/{id}")
    public Message query(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.GET, dictMapper.adapt(dictRepository.findById(id).orElse(null)));
    }

    @ApiOperation("分页查询字典")
    @GetMapping("/dict/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Long pid, String name) {
        Specification<Dict> specification = Specifications.<Dict>and()
                .eq(pid != null, "pid", pid)
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return PaginationUtil.execute(dictRepository.findAll(specification, pageable).map(dictMapper::adapt));
    }

    @ApiOperation("查询所有的字典（树形数据）")
    @GetMapping("/dict/tree")
    public Message queryToTree(String name) {
        Specification<Dict> specification = Specifications.<Dict>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return RESTful.success(RestTypeEnum.GET, dictLevelMapper.adapt(dictRepository.findAll(specification)));
    }

    @ApiOperation("查询所有的字典（子级列表）")
    @GetMapping("/dict/children/{code}")
    public Message queryToChildren(@PathVariable String code) {
        Optional<Dict> optional = dictRepository.findByCodeIgnoreCase(code);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找字典唯一标识码为【" + code + "】所对应的字典信息");
        }
        Dict dict = optional.get();
        Specification<Dict> specification = Specifications.<Dict>and()
                .like("level", StringUtils.join(LevelUtil.calculateLevel(dict.getLevel(), dict.getId()), "%"))
                .build();
        return RESTful.success(RestTypeEnum.GET, dictMapper.adapt(dictRepository.findAll(specification)));
    }

    @ApiOperation("修改一个字典")
    @PutMapping("/dict")
    public Message update(@Valid @RequestBody DictVM vm) {
        return RESTful.success(RestTypeEnum.PUT, dictService.update(vm));
    }

    @ApiOperation("删除一个菜单")
    @DeleteMapping("/dict/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, dictService.delete(id));
    }
}
