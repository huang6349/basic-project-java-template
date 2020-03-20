package org.hyl.modules.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.MyUser;
import org.hyl.modules.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Slf4j
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("【进行用户身份验证】: {}", login);

        return userRepository.findByUsernameIgnoreCase(login)
                .map(user -> createUserDetails(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("用户信息未找到 : " + login));
    }

    private User createUserDetails(String login, MyUser user) {
        if (GlobalConstants.DATA_DELETE_STATE.equals(user.getState())) {
            throw new UsernameNotFoundException("用户信息已删除 : " + login);
        }
        if (GlobalConstants.DATA_DISABLED_STATE.equals(user.getState())) {
            throw new UserNotActivatedException();
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode()))
                .collect(Collectors.toList());
        return new User(login, user.getPassword(), grantedAuthorities);
    }
}