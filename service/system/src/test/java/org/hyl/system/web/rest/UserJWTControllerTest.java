package org.hyl.system.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyl.auth.service.UserService;
import org.hyl.auth.web.rest.vm.LoginVM;
import org.hyl.auth.web.rest.vm.UserVM;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserJWTControllerTest {

    private static final String DEFAULT_USERNAME = "test01";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    public void authorize() throws Exception {
        UserVM userVM = new UserVM();
        userVM.setUsername(DEFAULT_USERNAME);
        userVM.setNickname("测试用户");
        userVM.setSexId(2L);
        userService.create(userVM);
        LoginVM vm = new LoginVM();
        vm.setUsername(DEFAULT_USERNAME);
        vm.setPassword("123456");
        ResultActions actions = mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
