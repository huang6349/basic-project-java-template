package org.hyl.modules.auth.service.mapper;

import org.hyl.modules.auth.domain.MyUser;
import org.hyl.modules.auth.domain.vm.UserVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public List<UserVM> adapt(List<MyUser> users) {
        return users.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public UserVM adapt(MyUser user) {
        return UserVM.adapt(user);
    }
}
