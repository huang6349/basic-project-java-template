package org.hyl.modules.auth.web.rest;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.modules.auth.security.jwt.JWTFilter;
import org.hyl.modules.auth.security.jwt.TokenProvider;
import org.hyl.modules.auth.domain.vm.LoginVM;
import org.hyl.modules.commons.result.Message;
import org.hyl.modules.commons.result.ResultUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @ApiOperation(value = "获取授权令牌")
    @PostMapping("/authenticate")
    public Message authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        httpServletResponse.setHeader(JWTFilter.AUTHORIZATION_HEADER, StrUtil.format("Bearer {}", jwt));
        return ResultUtil.success(StrUtil.format("欢迎回来"), new JWTToken(jwt));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class JWTToken {

        @JsonProperty("id_token")
        private String idToken;
    }
}
