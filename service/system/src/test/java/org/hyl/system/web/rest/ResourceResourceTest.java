package org.hyl.system.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hyl.system.domain.Resource;
import org.hyl.system.repository.ResourceRepository;
import org.hyl.system.service.PermissionsService;
import org.hyl.system.service.ResourceService;
import org.hyl.system.web.rest.vm.PermissionsVM;
import org.hyl.system.web.rest.vm.ResourceVM;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", password = "123456")
class ResourceResourceTest {

    private static final String DEFAULT_PATTERN = "测试菜单资源";
    private static final String UPDATE_PATTERN = "修改测试菜单资源";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PermissionsService permissionsService;

    private ResourceVM vm;

    @BeforeEach
    void setUp() {
        PermissionsVM permissionsVM = new PermissionsVM();
        permissionsVM.setName("测试菜单");
        PermissionsVM newPermissionsVM = permissionsService.create(permissionsVM);
        vm = new ResourceVM();
        vm.setPattern(DEFAULT_PATTERN);
        vm.setMethodId(5L);
        vm.setPermissionsId(newPermissionsVM.getId());
    }

    @Test
    public void create() throws Exception {
        List<Resource> prevAll = resourceRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Resource resource = currAll.get(currAll.size() - 1);
        Assertions.assertThat(resource.getId()).isNotNull();
        Assertions.assertThat(resource.getPattern()).isEqualTo(DEFAULT_PATTERN);
        Assertions.assertThat(resource.getMethod()).isNotNull();
        Assertions.assertThat(resource.getDesc()).isNull();
        Assertions.assertThat(resource.getPermissions()).isNotNull();
        Assertions.assertThat(resource.getCreatedBy()).isNotNull();
        Assertions.assertThat(resource.getCreatedDate()).isNotNull();
        Assertions.assertThat(resource.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(resource.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        resourceService.create(vm);
        ResultActions actions = mvc.perform(get("/api/resource")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        ResourceVM resourceVM = resourceService.create(vm);
        ResultActions actions = mvc.perform(get("/api/resource/" + resourceVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        resourceService.create(vm);
        ResultActions actions = mvc.perform(get("/api/resource/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        ResourceVM resourceVM = resourceService.create(vm);
        resourceVM.setPattern(UPDATE_PATTERN);
        List<Resource> prevAll = resourceRepository.findAll();
        Resource prevResource = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resourceVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Resource currResource = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currResource.getId()).isEqualTo(prevResource.getId());
        Assertions.assertThat(currResource.getPattern()).isEqualTo(UPDATE_PATTERN);
        Assertions.assertThat(currResource.getMethod()).isEqualTo(prevResource.getMethod());
        Assertions.assertThat(currResource.getDesc()).isEqualTo(prevResource.getDesc());
        Assertions.assertThat(currResource.getPermissions()).isEqualTo(prevResource.getPermissions());
        Assertions.assertThat(currResource.getCreatedBy()).isEqualTo(prevResource.getCreatedBy());
        Assertions.assertThat(currResource.getCreatedDate()).isEqualTo(prevResource.getCreatedDate());
        Assertions.assertThat(currResource.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currResource.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        ResourceVM resourceVM = resourceService.create(vm);
        List<Resource> prevAll = resourceRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/resource/" + resourceVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Resource> currAll = resourceRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 1);
    }
}
