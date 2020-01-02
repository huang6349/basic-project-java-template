package org.hyl.system.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.hyl.system.domain.MyUser;
import org.hyl.system.repository.UserRepository;
import org.hyl.system.service.AuthorityService;
import org.hyl.system.service.UserService;
import org.hyl.system.web.rest.vm.AuthorityVM;
import org.hyl.system.web.rest.vm.UserVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
public class UserResourceTest {

    private static final String DEFAULT_USERNAME = "test";
    private static final String DEFAULT_PASSWORD = "test123456";
    private static final String UPDATE_PASSWORD = "update123456";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserVM vm;

    @Before
    public void setup() {
        AuthorityVM authorityVM = new AuthorityVM();
        authorityVM.setName("测试角色");
        authorityVM.setCode("ROLE_TEST");
        AuthorityVM newAuthorityVM = authorityService.create(authorityVM);
        vm = new UserVM();
        vm.setUsername(DEFAULT_USERNAME);
        vm.setPassword(DEFAULT_PASSWORD);
        vm.setAuthorities(Sets.newHashSet(newAuthorityVM.getId()));
    }

    @Test
    public void create() throws Exception {
        List<MyUser> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<MyUser> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        MyUser user = currAll.get(currAll.size() - 1);
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getUsername()).isEqualTo(DEFAULT_USERNAME);
        Assertions.assertThat(passwordEncoder.matches(DEFAULT_PASSWORD, user.getPassword())).isTrue();
        Assertions.assertThat(user.getAuthorities()).hasSize(1);
        Assertions.assertThat(user.getCreatedBy()).isNotNull();
        Assertions.assertThat(user.getCreatedDate()).isNotNull();
        Assertions.assertThat(user.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(user.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        userService.create(vm);
        ResultActions actions = mvc.perform(get("/api/users")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        UserVM userVM = userService.create(vm);
        ResultActions actions = mvc.perform(get("/api/users/" + userVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        userService.create(vm);
        ResultActions actions = mvc.perform(get("/api/users/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        UserVM userVM = userService.create(vm);
        userVM.setPassword(UPDATE_PASSWORD);
        List<MyUser> prevAll = userRepository.findAll();
        MyUser prevUser = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<MyUser> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        MyUser currUser = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currUser.getId()).isEqualTo(prevUser.getId());
        Assertions.assertThat(currUser.getUsername()).isEqualTo(prevUser.getUsername());
        Assertions.assertThat(currUser.getPassword()).isEqualTo(UPDATE_PASSWORD);
        Assertions.assertThat(currUser.getAuthorities()).isEqualTo(prevUser.getAuthorities());
        Assertions.assertThat(currUser.getCreatedBy()).isEqualTo(prevUser.getCreatedBy());
        Assertions.assertThat(currUser.getCreatedDate()).isEqualTo(prevUser.getCreatedDate());
        Assertions.assertThat(currUser.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currUser.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        UserVM userVM = userService.create(vm);
        List<MyUser> prevAll = userRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/users/" + userVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<MyUser> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 1);
    }
}
