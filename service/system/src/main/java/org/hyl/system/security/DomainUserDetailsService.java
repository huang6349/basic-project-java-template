package org.hyl.system.security;

import org.hyl.data.config.DataConstants;
import org.hyl.system.domain.MyUser;
import org.hyl.system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("进行身份验证 {}", login);

        return userRepository.findByUsernameIgnoreCase(login)
                .map(user -> createUserDetails(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("您输入的帐号或者密码不正确，请重试"));
    }

    private User createUserDetails(String login, MyUser user) {
        if (!DataConstants.DATA_NORMAL_STATE.equals(user.getState())) {
            throw new UserNotActivatedException("您的账号已被禁用，请联系管理员");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList());
        return new User(login, user.getPassword(), grantedAuthorities);
    }
}
