package org.hyl.modules.auth.security.jwt;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.Authority;
import org.hyl.modules.auth.domain.MyUser;
import org.hyl.modules.auth.repository.UserRepository;
import org.hyl.modules.auth.security.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private Long tokenValidityInMilliseconds;

    private final SecurityProperties securityProperties;

    private final UserRepository userRepository;

    public TokenProvider(SecurityProperties securityProperties, UserRepository userRepository) {
        this.securityProperties = securityProperties;
        this.userRepository = userRepository;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes;
        String secret = securityProperties.getSecret();
        if (!StrUtil.isEmpty(secret)) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            keyBytes = Decoders.BASE64.decode(securityProperties.getBase64Secret());
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = 1000 * securityProperties.getTokenValidityInSeconds();
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        JwtBuilder builder = Jwts.builder();
        builder.setSubject(authentication.getName());
        builder.claim(AUTHORITIES_KEY, authorities);
        builder.signWith(key, SignatureAlgorithm.HS512);
        builder.setExpiration(new Date(now + this.tokenValidityInMilliseconds));
        builder.setNotBefore(new Date(now));
        return builder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Optional<MyUser> optional = userRepository.findByUsernameIgnoreCase(claims.getSubject());
        if (!optional.isPresent()) return null;

        MyUser user = optional.get();
        if (GlobalConstants.DATA_DISABLED_STATE.equals(user.getState())) return null;
        String authorities = claims.get(AUTHORITIES_KEY).toString();
        if (!user.getAuthorities().stream().map(Authority::getCode).sorted().collect(Collectors.joining(",")).equals(authorities)) {
            return null;
        }

        Collection<? extends GrantedAuthority> grantedAuthorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("无效的 JWT 令牌");
        } catch (ExpiredJwtException e) {
            log.info("已过期的 JWT 令牌");
        } catch (UnsupportedJwtException e) {
            log.info("不支持的 JWT 令牌");
        } catch (IllegalArgumentException e) {
            log.info("参数不存在的 JWT 令牌");
        }
        return false;
    }
}
