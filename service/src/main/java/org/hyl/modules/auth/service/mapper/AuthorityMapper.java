package org.hyl.modules.auth.service.mapper;

import org.hyl.modules.auth.domain.Authority;
import org.hyl.modules.auth.domain.vm.AuthorityVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthorityMapper {

    public List<AuthorityVM> adapt(List<Authority> authorities) {
        return authorities.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public AuthorityVM adapt(Authority authority) {
        return AuthorityVM.adapt(authority);
    }
}

