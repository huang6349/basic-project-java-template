package org.hyl.system.web.rest;

import com.github.wenhao.jpa.Specifications;
import org.apache.commons.lang3.StringUtils;
import org.hyl.system.commons.pagination.PaginationUtil;
import org.hyl.system.commons.result.RESTful;
import org.hyl.system.commons.result.enums.RestTypeEnum;
import org.hyl.system.domain.MyUser;
import org.hyl.system.errors.DataNotAlreadyIDException;
import org.hyl.system.repository.UserRepository;
import org.hyl.system.service.UserService;
import org.hyl.system.web.rest.vm.UpdateUserVM;
import org.hyl.system.web.rest.vm.UserVM;
import org.hyl.system.commons.result.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public Message create(@Valid @RequestBody UserVM vm) {
        if (vm.getId() != null) {
            throw new DataNotAlreadyIDException();
        }
        return RESTful.success(RestTypeEnum.POST, userService.create(vm));
    }

    @GetMapping("/users")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, userRepository.findAll().stream().map(UserVM::adapt));
    }

    @GetMapping("/users/{id}")
    public Message query(@PathVariable Long id) {
        Optional<MyUser> optional = userRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(UserVM::adapt).orElse(null));
    }

    @GetMapping("/users/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String username) {
        Specification<MyUser> specification = Specifications.<MyUser>and()
                .like(StringUtils.isNotBlank(username), "username", "%" + StringUtils.trim(username) + "%")
                .build();
        return PaginationUtil.execute(userRepository.findAll(specification, pageable).map(UserVM::adapt));
    }

    @PutMapping("/users")
    public Message update(@Valid @RequestBody UpdateUserVM vm) {
        return RESTful.success(RestTypeEnum.PUT, userService.update(vm));
    }

    @DeleteMapping("/users/{id}")
    public Message delete(@PathVariable Long id) {
        return RESTful.success(RestTypeEnum.DELETE, userService.delete(id));
    }
}
