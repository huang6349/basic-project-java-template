package org.hyl.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import org.hyl.repository.AuthorityRepository;
import org.hyl.service.AuthorityService;
import org.hyl.service.PermissionsService;
import org.hyl.web.rest.vm.AuthorityVM;
import org.hyl.web.rest.vm.PermissionsVM;
import org.hyl.web.rest.vm.UpdateAuthorityPermissionsVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthorityResourceTest {

    private static final String DEFAULT_NAME = "测试角色";
    private static final String DEFAULT_CODE = "ROLE_TEST";
    private static final String UPDATE_NAME = "修改测试角色";
    private static final String UPDATE_CODE = DEFAULT_CODE;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PermissionsService permissionsService;

    @Test
    public void create() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
        ResultActions actions = mvc.perform(post("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void query() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
        authorityService.create(vm);
        ResultActions actions = mvc.perform(get("/api/authority")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
        AuthorityVM newAuthorityVM = authorityService.create(vm);
        ResultActions actions = mvc.perform(get("/api/authority/" + newAuthorityVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
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
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
        AuthorityVM newAuthorityVM = authorityService.create(vm);
        newAuthorityVM.setName(UPDATE_NAME);
        newAuthorityVM.setCode(UPDATE_CODE);
        ResultActions actions = mvc.perform(put("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAuthorityVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void updateAuthorityPermissions() throws Exception {
        PermissionsVM permissionsVM = new PermissionsVM();
        permissionsVM.setName("测试权限");
        permissionsVM.setPid(0L);
        PermissionsVM newPermissionsVM = permissionsService.create(permissionsVM);
        AuthorityVM authorityVM = new AuthorityVM();
        authorityVM.setName(DEFAULT_NAME);
        authorityVM.setCode(DEFAULT_CODE);
        AuthorityVM newAuthorityVM = authorityService.create(authorityVM);
        UpdateAuthorityPermissionsVM vm = new UpdateAuthorityPermissionsVM();
        vm.setId(newAuthorityVM.getId());
        vm.setPermissions(Sets.newHashSet(newPermissionsVM.getId()));
        ResultActions actions = mvc.perform(put("/api/authority/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void delete() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
        AuthorityVM newAuthorityVM = authorityService.create(vm);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/authority/" + newAuthorityVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
