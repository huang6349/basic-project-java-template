package org.hyl.system.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private String key = "basic";

    private Long tokenValidityInMilliseconds;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.tokenValidityInMilliseconds = 1000 * 2592000L;
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
        builder.signWith(SignatureAlgorithm.HS512, key);
        builder.setExpiration(new Date(now + this.tokenValidityInMilliseconds));
        builder.setNotBefore(new Date(now));
        return builder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> grantedAuthorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(principal, token, grantedAuthorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
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
