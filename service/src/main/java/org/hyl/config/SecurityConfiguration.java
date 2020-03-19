package org.hyl.config;

import org.hyl.modules.auth.security.jwt.JWTConfigurer;
import org.hyl.modules.auth.security.jwt.TokenProvider;
import org.hyl.modules.auth.service.SecurityMessageSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;

    private final SecurityMessageSupport securityMessageSupport;

    public SecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter, SecurityMessageSupport securityMessageSupport) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.securityMessageSupport = securityMessageSupport;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/**/*.{js,html}")
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(securityMessageSupport);
        http.csrf().disable();
        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.exceptionHandling().authenticationEntryPoint(securityMessageSupport);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/file/upload").permitAll()
                .antMatchers("/api/file/download/*").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/api/**").access("@rbacAuthorityService.hasPermission(request, authentication)");
        http.apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
