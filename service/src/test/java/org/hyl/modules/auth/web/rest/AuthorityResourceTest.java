package org.hyl.modules.auth.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.hyl.modules.auth.domain.Authority;
import org.hyl.modules.auth.repository.AuthorityRepository;
import org.hyl.modules.auth.service.AuthorityService;
import org.hyl.modules.auth.service.PermissionService;
import org.hyl.modules.auth.domain.vm.AuthorityVM;
import org.hyl.modules.auth.domain.vm.PermissionVM;
import org.hyl.modules.auth.domain.vm.UpdateAuthorityPermissionsVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class AuthorityResourceTest {

    private static final String DEFAULT_NAME = "测试角色";
    private static final String DEFAULT_CODE = "TEST";
    private static final String UPDATE_NAME = "修改测试角色";
    private static final String UPDATE_CODE = "UPDATETEST";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PermissionService permissionService;

    private AuthorityVM vm;

    @BeforeEach
    public void setUp() {
        vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
    }

    @Test
    public void create() throws Exception {
        List<Authority> prevAll = authorityRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Authority authority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(authority.getId()).isNotNull();
        Assertions.assertThat(authority.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(authority.getCode()).isEqualTo(StringUtils.join("ROLE_", DEFAULT_CODE));
        Assertions.assertThat(authority.getDesc()).isNull();
        Assertions.assertThat(authority.getUsers()).isEmpty();
        Assertions.assertThat(authority.getPermissions()).isEmpty();
        Assertions.assertThat(authority.getCreatedBy()).isNotNull();
        Assertions.assertThat(authority.getCreatedDate()).isNotNull();
        Assertions.assertThat(authority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(authority.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        authorityService.create(vm);
        ResultActions actions = mvc.perform(get("/api/authority")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        AuthorityVM authorityVM = authorityService.create(vm);
        ResultActions actions = mvc.perform(get("/api/authority/" + authorityVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        authorityService.create(vm);
        ResultActions actions = mvc.perform(get("/api/authority/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        AuthorityVM authorityVM = authorityService.create(vm);
        authorityVM.setName(UPDATE_NAME);
        authorityVM.setCode(UPDATE_CODE);
        List<Authority> prevAll = authorityRepository.findAll();
        Authority prevAuthority = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorityVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Authority currAuthority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currAuthority.getId()).isEqualTo(prevAuthority.getId());
        Assertions.assertThat(currAuthority.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currAuthority.getCode()).isEqualTo(StringUtils.join("ROLE_", UPDATE_CODE));
        Assertions.assertThat(currAuthority.getDesc()).isEqualTo(prevAuthority.getDesc());
        Assertions.assertThat(currAuthority.getUsers()).isEqualTo(prevAuthority.getUsers());
        Assertions.assertThat(currAuthority.getPermissions()).isEqualTo(prevAuthority.getPermissions());
        Assertions.assertThat(currAuthority.getCreatedBy()).isEqualTo(prevAuthority.getCreatedBy());
        Assertions.assertThat(currAuthority.getCreatedDate()).isEqualTo(prevAuthority.getCreatedDate());
        Assertions.assertThat(currAuthority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currAuthority.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void updateAuthorityPermissions() throws Exception {
        AuthorityVM authorityVM = authorityService.create(vm);
        UpdateAuthorityPermissionsVM vm = new UpdateAuthorityPermissionsVM();
        vm.setId(authorityVM.getId());
        PermissionVM permissionVM = new PermissionVM();
        permissionVM.setName("测试菜单");
        permissionVM.setPid(0L);
        PermissionVM newPermissionVM = permissionService.create(permissionVM);
        vm.setPermissions(Sets.newHashSet(newPermissionVM.getId()));
        List<Authority> prevAll = authorityRepository.findAll();
        Authority prevAuthority = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/authority/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Authority currAuthority = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currAuthority.getId()).isEqualTo(prevAuthority.getId());
        Assertions.assertThat(currAuthority.getName()).isEqualTo(prevAuthority.getName());
        Assertions.assertThat(currAuthority.getCode()).isEqualTo(prevAuthority.getCode());
        Assertions.assertThat(currAuthority.getDesc()).isEqualTo(prevAuthority.getDesc());
        Assertions.assertThat(currAuthority.getUsers()).isEqualTo(prevAuthority.getUsers());
        Assertions.assertThat(currAuthority.getPermissions()).hasSize(1);
        Assertions.assertThat(currAuthority.getCreatedBy()).isEqualTo(prevAuthority.getCreatedBy());
        Assertions.assertThat(currAuthority.getCreatedDate()).isEqualTo(prevAuthority.getCreatedDate());
        Assertions.assertThat(currAuthority.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currAuthority.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        AuthorityVM authorityVM = authorityService.create(vm);
        List<Authority> prevAll = authorityRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/authority/" + authorityVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Authority> currAll = authorityRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 1);
    }
}
