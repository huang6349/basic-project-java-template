package org.hyl.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyl.config.Constants;
import org.hyl.service.UserService;
import org.hyl.web.rest.vm.LoginVM;
import org.hyl.web.rest.vm.UserVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserJWTControllerTest {

    private static final String DEFAULT_USERNAME = "test";
    private static final String DEFAULT_PASSWORD = "test123456";

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
        userVM.setPassword(DEFAULT_PASSWORD);
        userService.create(userVM);
        LoginVM vm = new LoginVM();
        vm.setUsername(DEFAULT_USERNAME);
        vm.setPassword(DEFAULT_PASSWORD);
        ResultActions actions = mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
