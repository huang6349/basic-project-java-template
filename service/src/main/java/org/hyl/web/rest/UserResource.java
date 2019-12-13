package org.hyl.web.rest;

import org.hyl.commons.result.Message;
import org.hyl.commons.result.PaginationUtil;
import org.hyl.commons.result.enums.rest.RestTypeEnum;
import org.hyl.commons.result.rest.RESTful;
import org.hyl.domain.MyUser;
import org.hyl.errors.DataNotAlreadyIDException;
import org.hyl.repository.UserRepository;
import org.hyl.service.UserService;
import org.hyl.web.rest.vm.UserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public Message create(@Valid @RequestBody UserVM vm) {
        log.debug("新增用户：{}", vm);

        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, userService.create(vm));
    }

    @GetMapping("/users")
    public Message query() {
        log.debug("根据条件查询用户：{}");

        return RESTful.success(RestTypeEnum.GET, userRepository.findAll().stream().map(UserVM::adapt));
    }

    @GetMapping("/users/{id}")
    public Message query(@PathVariable Long id) {
        log.debug("根据编号查询用户：{}", id);

        Optional<MyUser> optional = userRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(UserVM::adapt).orElse(null));
    }

    @GetMapping("/users/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("分页查询用户：{}", pageable);

        return PaginationUtil.execute(userRepository.findAll(pageable).map(UserVM::adapt));
    }

    @PutMapping("/users")
    public Message update(@Valid @RequestBody UserVM vm) {
        log.debug("修改用户：{}", vm);

        return RESTful.success(RestTypeEnum.PUT, userService.update(vm));
    }

    @DeleteMapping("/users/{id}")
    public Message delete(@PathVariable Long id) {
        log.debug("删除用户：{}", id);

        return RESTful.success(RestTypeEnum.DELETE, userService.delete(id));
    }
}
