package org.hyl.modules.auth.security;

import cn.hutool.core.util.StrUtil;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.MyUser;
import org.hyl.modules.auth.repository.UserRepository;
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

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        return userRepository.findByUsernameIgnoreCase(login)
                .map(user -> createUserDetails(login, user))
                .orElseThrow(() -> new UsernameNotFoundException(StrUtil.format("User {} was not found in the database", login)));
    }

    private User createUserDetails(String login, MyUser user) {
        if (GlobalConstants.DATA_DELETE_STATE.equals(user.getState())) {
            throw new UsernameNotFoundException(StrUtil.format("User {} has been removed from the database", login));
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
