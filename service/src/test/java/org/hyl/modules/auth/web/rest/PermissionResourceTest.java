package org.hyl.modules.auth.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.hyl.modules.auth.domain.Permission;
import org.hyl.modules.auth.repository.PermissionRepository;
import org.hyl.modules.auth.service.PermissionService;
import org.hyl.modules.auth.domain.vm.PermissionVM;
import org.hyl.modules.data.auditing.utils.LevelUtil;
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
public class PermissionResourceTest {

    private static final String DEFAULT_NAME = "测试菜单";
    private static final String UPDATE_NAME = "修改测试菜单";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    private PermissionVM vm;

    @BeforeEach
    public void setUp() {
        vm = new PermissionVM();
        vm.setName(DEFAULT_NAME);
    }

    @Test
    public void create() throws Exception {
        List<Permission> prevAll = permissionRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Permission permission = currAll.get(currAll.size() - 1);
        Assertions.assertThat(permission.getId()).isNotNull();
        Assertions.assertThat(permission.getPid()).isEqualTo(0);
        Assertions.assertThat(permission.getLevel()).isEqualTo(StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
        Assertions.assertThat(permission.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(permission.getPath()).isNull();
        Assertions.assertThat(permission.getIcon()).isNull();
        Assertions.assertThat(permission.getSeq()).isEqualTo(0);
        Assertions.assertThat(permission.getDesc()).isNull();
        Assertions.assertThat(permission.getAuthorities()).isEmpty();
        Assertions.assertThat(permission.getResources()).isEmpty();
        Assertions.assertThat(permission.getCreatedBy()).isNotNull();
        Assertions.assertThat(permission.getCreatedDate()).isNotNull();
        Assertions.assertThat(permission.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(permission.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        permissionService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        PermissionVM permissionVM = permissionService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions/" + permissionVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        permissionService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryToTree() throws Exception {
        PermissionVM permissionVM = permissionService.create(vm);
        PermissionVM vm1 = new PermissionVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setPid(permissionVM.getId());
        permissionService.create(vm1);
        PermissionVM vm2 = new PermissionVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setPid(permissionVM.getId());
        permissionService.create(vm2);
        ResultActions actions = mvc.perform(get("/api/permissions/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        PermissionVM parentPermissionVM = new PermissionVM();
        parentPermissionVM.setName("父级测试菜单");
        PermissionVM newParentPermissionVM = permissionService.create(parentPermissionVM);
        PermissionVM permissionVM = permissionService.create(vm);
        permissionVM.setName(UPDATE_NAME);
        permissionVM.setPid(newParentPermissionVM.getId());
        List<Permission> prevAll = permissionRepository.findAll();
        Permission prevPermission = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permissionVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Permission currPermission = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currPermission.getId()).isEqualTo(prevPermission.getId());
        Assertions.assertThat(currPermission.getPid()).isEqualTo(newParentPermissionVM.getId());
        Assertions.assertThat(currPermission.getLevel()).isEqualTo(LevelUtil.calculateLevel("0E", newParentPermissionVM.getId()));
        Assertions.assertThat(currPermission.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currPermission.getPath()).isEqualTo(prevPermission.getPath());
        Assertions.assertThat(currPermission.getIcon()).isEqualTo(prevPermission.getIcon());
        Assertions.assertThat(currPermission.getSeq()).isEqualTo(prevPermission.getSeq());
        Assertions.assertThat(currPermission.getDesc()).isEqualTo(prevPermission.getDesc());
        Assertions.assertThat(currPermission.getAuthorities()).isEqualTo(prevPermission.getAuthorities());
        Assertions.assertThat(currPermission.getResources()).isEqualTo(prevPermission.getResources());
        Assertions.assertThat(currPermission.getCreatedBy()).isEqualTo(prevPermission.getCreatedBy());
        Assertions.assertThat(currPermission.getCreatedDate()).isEqualTo(prevPermission.getCreatedDate());
        Assertions.assertThat(currPermission.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currPermission.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        PermissionVM permissionVM = permissionService.create(vm);
        PermissionVM vm1 = new PermissionVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setPid(permissionVM.getId());
        permissionService.create(vm1);
        PermissionVM vm2 = new PermissionVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setPid(permissionVM.getId());
        permissionService.create(vm2);
        List<Permission> prevAll = permissionRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/permissions/" + permissionVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permission> currAll = permissionRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 3);
    }
}
