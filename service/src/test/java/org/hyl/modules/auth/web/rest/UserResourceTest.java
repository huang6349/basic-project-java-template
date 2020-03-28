package org.hyl.modules.auth.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.assertj.core.api.Assertions;
import org.hyl.config.GlobalConstants;
import org.hyl.modules.auth.domain.MyUser;
import org.hyl.modules.auth.domain.MyUserInfo;
import org.hyl.modules.auth.repository.UserRepository;
import org.hyl.modules.auth.service.AuthorityService;
import org.hyl.modules.auth.service.UserService;
import org.hyl.modules.auth.domain.vm.AuthorityVM;
import org.hyl.modules.auth.domain.vm.UserVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
public class UserResourceTest {

    private static final String DEFAULT_USERNAME = "test01";
    private static final String DEFAULT_NICKNAME = "测试用户";
    private static final String DEFAULT_PASSWORD = "123456";
    private static final String UPDATE_NICKNAME = "修改测试用户";

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

    @BeforeEach
    public void setUp() {
        AuthorityVM authorityVM = new AuthorityVM();
        authorityVM.setName("测试角色");
        authorityVM.setCode("TEST");
        AuthorityVM newAuthorityVM = authorityService.create(authorityVM);
        vm = new UserVM();
        vm.setUsername(DEFAULT_USERNAME);
        vm.setNickname(DEFAULT_NICKNAME);
        vm.setSexId(10001L);
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
        MyUserInfo info = user.getInfo();
        Assertions.assertThat(info.getId()).isNotNull();
        Assertions.assertThat(info.getNickname()).isEqualTo(DEFAULT_NICKNAME);
        Assertions.assertThat(info.getRealname()).isNull();
        Assertions.assertThat(info.getSex()).isNotNull();
        Assertions.assertThat(info.getBirthday()).isNull();
        Assertions.assertThat(info.getIdCard()).isNull();
        Assertions.assertThat(info.getEmail()).isNull();
        Assertions.assertThat(info.getMobilePhone()).isNull();
        Assertions.assertThat(info.getCreatedBy()).isNotNull();
        Assertions.assertThat(info.getCreatedDate()).isNotNull();
        Assertions.assertThat(info.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(info.getLastModifiedDate()).isNotNull();
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
        userVM.setNickname(UPDATE_NICKNAME);
        userVM.setSexId(10002L);
        List<MyUser> prevAll = userRepository.findAll();
        MyUser prevUser = prevAll.get(prevAll.size() - 1);
        MyUserInfo prevInfo = prevUser.getInfo();
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
        Assertions.assertThat(currUser.getPassword()).isEqualTo(prevUser.getPassword());
        Assertions.assertThat(currUser.getAuthorities()).isEqualTo(prevUser.getAuthorities());
        Assertions.assertThat(currUser.getCreatedBy()).isEqualTo(prevUser.getCreatedBy());
        Assertions.assertThat(currUser.getCreatedDate()).isEqualTo(prevUser.getCreatedDate());
        Assertions.assertThat(currUser.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currUser.getLastModifiedDate()).isNotNull();
        MyUserInfo currInfo = currUser.getInfo();
        Assertions.assertThat(currInfo.getId()).isEqualTo(prevInfo.getId());
        Assertions.assertThat(currInfo.getNickname()).isEqualTo(UPDATE_NICKNAME);
        Assertions.assertThat(currInfo.getRealname()).isEqualTo(prevInfo.getRealname());
        Assertions.assertThat(currInfo.getSex()).isNotNull();
        Assertions.assertThat(currInfo.getBirthday()).isEqualTo(prevInfo.getBirthday());
        Assertions.assertThat(currInfo.getIdCard()).isEqualTo(prevInfo.getIdCard());
        Assertions.assertThat(currInfo.getEmail()).isEqualTo(prevInfo.getEmail());
        Assertions.assertThat(currInfo.getMobilePhone()).isEqualTo(prevInfo.getMobilePhone());
        Assertions.assertThat(currInfo.getCreatedBy()).isEqualTo(prevInfo.getCreatedBy());
        Assertions.assertThat(currInfo.getCreatedDate()).isEqualTo(prevInfo.getCreatedDate());
        Assertions.assertThat(currInfo.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currInfo.getLastModifiedDate()).isNotNull();
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

    @Test
    public void enable() throws Exception {
        UserVM userVM = userService.create(vm);
        List<MyUser> prevAll = userRepository.findAll();
        MyUser prevUser = prevAll.get(prevAll.size() - 1);
        MyUserInfo prevInfo = prevUser.getInfo();
        Assertions.assertThat(GlobalConstants.DATA_DISABLED_STATE).isEqualTo(userService.disable(prevUser.getId()).getState());
        ResultActions actions = mvc.perform(put("/api/users/enable/" + userVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<MyUser> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        MyUser currUser = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currUser.getId()).isEqualTo(prevUser.getId());
        Assertions.assertThat(currUser.getUsername()).isEqualTo(prevUser.getUsername());
        Assertions.assertThat(currUser.getPassword()).isEqualTo(prevUser.getPassword());
        Assertions.assertThat(currUser.getAuthorities()).isEqualTo(prevUser.getAuthorities());
        Assertions.assertThat(currUser.getCreatedBy()).isEqualTo(prevUser.getCreatedBy());
        Assertions.assertThat(currUser.getCreatedDate()).isEqualTo(prevUser.getCreatedDate());
        Assertions.assertThat(currUser.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currUser.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(currUser.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
        MyUserInfo currInfo = currUser.getInfo();
        Assertions.assertThat(currInfo.getId()).isEqualTo(prevInfo.getId());
        Assertions.assertThat(currInfo.getNickname()).isEqualTo(prevInfo.getNickname());
        Assertions.assertThat(currInfo.getRealname()).isEqualTo(prevInfo.getRealname());
        Assertions.assertThat(currInfo.getSex()).isNotNull();
        Assertions.assertThat(currInfo.getBirthday()).isEqualTo(prevInfo.getBirthday());
        Assertions.assertThat(currInfo.getIdCard()).isEqualTo(prevInfo.getIdCard());
        Assertions.assertThat(currInfo.getEmail()).isEqualTo(prevInfo.getEmail());
        Assertions.assertThat(currInfo.getMobilePhone()).isEqualTo(prevInfo.getMobilePhone());
        Assertions.assertThat(currInfo.getCreatedBy()).isEqualTo(prevInfo.getCreatedBy());
        Assertions.assertThat(currInfo.getCreatedDate()).isEqualTo(prevInfo.getCreatedDate());
        Assertions.assertThat(currInfo.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currInfo.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(currInfo.getState()).isEqualTo(GlobalConstants.DATA_NORMAL_STATE);
    }

    @Test
    public void disable() throws Exception {
        UserVM userVM = userService.create(vm);
        List<MyUser> prevAll = userRepository.findAll();
        MyUser prevUser = prevAll.get(prevAll.size() - 1);
        MyUserInfo prevInfo = prevUser.getInfo();
        Assertions.assertThat(GlobalConstants.DATA_NORMAL_STATE).isEqualTo(prevUser.getState());
        ResultActions actions = mvc.perform(put("/api/users/disable/" + userVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<MyUser> currAll = userRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        MyUser currUser = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currUser.getId()).isEqualTo(prevUser.getId());
        Assertions.assertThat(currUser.getUsername()).isEqualTo(prevUser.getUsername());
        Assertions.assertThat(currUser.getPassword()).isEqualTo(prevUser.getPassword());
        Assertions.assertThat(currUser.getAuthorities()).isEqualTo(prevUser.getAuthorities());
        Assertions.assertThat(currUser.getCreatedBy()).isEqualTo(prevUser.getCreatedBy());
        Assertions.assertThat(currUser.getCreatedDate()).isEqualTo(prevUser.getCreatedDate());
        Assertions.assertThat(currUser.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currUser.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(currUser.getState()).isEqualTo(GlobalConstants.DATA_DISABLED_STATE);
        MyUserInfo currInfo = currUser.getInfo();
        Assertions.assertThat(currInfo.getId()).isEqualTo(prevInfo.getId());
        Assertions.assertThat(currInfo.getNickname()).isEqualTo(prevInfo.getNickname());
        Assertions.assertThat(currInfo.getRealname()).isEqualTo(prevInfo.getRealname());
        Assertions.assertThat(currInfo.getSex()).isNotNull();
        Assertions.assertThat(currInfo.getBirthday()).isEqualTo(prevInfo.getBirthday());
        Assertions.assertThat(currInfo.getIdCard()).isEqualTo(prevInfo.getIdCard());
        Assertions.assertThat(currInfo.getEmail()).isEqualTo(prevInfo.getEmail());
        Assertions.assertThat(currInfo.getMobilePhone()).isEqualTo(prevInfo.getMobilePhone());
        Assertions.assertThat(currInfo.getCreatedBy()).isEqualTo(prevInfo.getCreatedBy());
        Assertions.assertThat(currInfo.getCreatedDate()).isEqualTo(prevInfo.getCreatedDate());
        Assertions.assertThat(currInfo.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currInfo.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(currInfo.getState()).isEqualTo(GlobalConstants.DATA_DISABLED_STATE);
    }

    @Test
    public void resetPassword() throws Exception {
        UserVM userVM = userService.create(vm);
        ResultActions actions = mvc.perform(put("/api/users/password/reset/" + userVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
