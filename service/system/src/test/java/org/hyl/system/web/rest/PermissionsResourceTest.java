package org.hyl.system.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.auth.domain.Permissions;
import org.hyl.auth.repository.PermissionsRepository;
import org.hyl.auth.service.PermissionsService;
import org.hyl.auth.web.rest.vm.PermissionsVM;
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
public class PermissionsResourceTest {

    private static final String DEFAULT_NAME = "测试菜单";
    private static final String UPDATE_NAME = "修改测试菜单";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private PermissionsService permissionsService;

    private PermissionsVM vm;

    @BeforeEach
    public void setUp() {
        vm = new PermissionsVM();
        vm.setName(DEFAULT_NAME);
    }

    @Test
    public void create() throws Exception {
        List<Permissions> prevAll = permissionsRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permissions> currAll = permissionsRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Permissions permissions = currAll.get(currAll.size() - 1);
        Assertions.assertThat(permissions.getId()).isNotNull();
        Assertions.assertThat(permissions.getPid()).isEqualTo(0);
        Assertions.assertThat(permissions.getLevel()).isEqualTo(StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
        Assertions.assertThat(permissions.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(permissions.getPath()).isNull();
        Assertions.assertThat(permissions.getIcon()).isNull();
        Assertions.assertThat(permissions.getSeq()).isEqualTo(0);
        Assertions.assertThat(permissions.getDesc()).isNull();
        Assertions.assertThat(permissions.getAuthorities()).isEmpty();
        Assertions.assertThat(permissions.getResources()).isEmpty();
        Assertions.assertThat(permissions.getCreatedBy()).isNotNull();
        Assertions.assertThat(permissions.getCreatedDate()).isNotNull();
        Assertions.assertThat(permissions.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(permissions.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        permissionsService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        PermissionsVM permissionsVM = permissionsService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions/" + permissionsVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        permissionsService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryToTree() throws Exception {
        PermissionsVM permissionsVM = permissionsService.create(vm);
        PermissionsVM vm1 = new PermissionsVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setPid(permissionsVM.getId());
        permissionsService.create(vm1);
        PermissionsVM vm2 = new PermissionsVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setPid(permissionsVM.getId());
        permissionsService.create(vm2);
        ResultActions actions = mvc.perform(get("/api/permissions/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        PermissionsVM parentPermissionsVM = new PermissionsVM();
        parentPermissionsVM.setName("父级测试菜单");
        PermissionsVM newParentPermissionsVM = permissionsService.create(parentPermissionsVM);
        PermissionsVM permissionsVM = permissionsService.create(vm);
        permissionsVM.setName(UPDATE_NAME);
        permissionsVM.setPid(newParentPermissionsVM.getId());
        List<Permissions> prevAll = permissionsRepository.findAll();
        Permissions prevPermissions = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permissionsVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permissions> currAll = permissionsRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Permissions currPermissions = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currPermissions.getId()).isEqualTo(prevPermissions.getId());
        Assertions.assertThat(currPermissions.getPid()).isEqualTo(newParentPermissionsVM.getId());
        Assertions.assertThat(currPermissions.getLevel()).isEqualTo(LevelUtil.calculateLevel("0E", newParentPermissionsVM.getId()));
        Assertions.assertThat(currPermissions.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currPermissions.getPath()).isEqualTo(prevPermissions.getPath());
        Assertions.assertThat(currPermissions.getIcon()).isEqualTo(prevPermissions.getIcon());
        Assertions.assertThat(currPermissions.getSeq()).isEqualTo(prevPermissions.getSeq());
        Assertions.assertThat(currPermissions.getDesc()).isEqualTo(prevPermissions.getDesc());
        Assertions.assertThat(currPermissions.getAuthorities()).isEqualTo(prevPermissions.getAuthorities());
        Assertions.assertThat(currPermissions.getResources()).isEqualTo(prevPermissions.getResources());
        Assertions.assertThat(currPermissions.getCreatedBy()).isEqualTo(prevPermissions.getCreatedBy());
        Assertions.assertThat(currPermissions.getCreatedDate()).isEqualTo(prevPermissions.getCreatedDate());
        Assertions.assertThat(currPermissions.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currPermissions.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        PermissionsVM permissionsVM = permissionsService.create(vm);
        PermissionsVM vm1 = new PermissionsVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setPid(permissionsVM.getId());
        permissionsService.create(vm1);
        PermissionsVM vm2 = new PermissionsVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setPid(permissionsVM.getId());
        permissionsService.create(vm2);
        List<Permissions> prevAll = permissionsRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/permissions/" + permissionsVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Permissions> currAll = permissionsRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 3);
    }
}
