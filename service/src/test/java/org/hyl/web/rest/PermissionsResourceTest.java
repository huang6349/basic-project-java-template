package org.hyl.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyl.service.PermissionsService;
import org.hyl.web.rest.vm.PermissionsVM;
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
public class PermissionsResourceTest {

    private static final String DEFAULT_NAME = "测试权限";
    private static final String UPDATE_NAME = "修改测试权限";
    private static final Long DEFAULT_PID = 0L;
    private static final Long UPDATE_PID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionsService permissionsService;

    @Test
    public void create() throws Exception {
        PermissionsVM vm = new PermissionsVM();
        vm.setName(DEFAULT_NAME);
        vm.setPid(DEFAULT_PID);
        ResultActions actions = mvc.perform(post("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void query() throws Exception {
        PermissionsVM vm = new PermissionsVM();
        vm.setName(DEFAULT_NAME);
        vm.setPid(DEFAULT_PID);
        permissionsService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        PermissionsVM vm = new PermissionsVM();
        vm.setName(DEFAULT_NAME);
        vm.setPid(DEFAULT_PID);
        PermissionsVM newPermissionsVM = permissionsService.create(vm);
        ResultActions actions = mvc.perform(get("/api/permissions/" + newPermissionsVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        PermissionsVM vm = new PermissionsVM();
        vm.setName(DEFAULT_NAME);
        vm.setPid(DEFAULT_PID);
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
        PermissionsVM vm1 = new PermissionsVM();
        vm1.setName(DEFAULT_NAME);
        vm1.setPid(DEFAULT_PID);
        PermissionsVM newPermissionsVM = permissionsService.create(vm1);
        PermissionsVM vm2 = new PermissionsVM();
        vm2.setName(DEFAULT_NAME + "A");
        vm2.setPid(newPermissionsVM.getId());
        permissionsService.create(vm2);
        PermissionsVM vm3 = new PermissionsVM();
        vm3.setName(DEFAULT_NAME + "B");
        vm3.setPid(newPermissionsVM.getId());
        permissionsService.create(vm3);
        ResultActions actions = mvc.perform(get("/api/permissions/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        PermissionsVM vm1 = new PermissionsVM();
        vm1.setName(DEFAULT_NAME);
        vm1.setPid(DEFAULT_PID);
        PermissionsVM newPermissionsVM = permissionsService.create(vm1);
        PermissionsVM vm2 = new PermissionsVM();
        vm2.setName(DEFAULT_NAME + "A");
        vm2.setPid(newPermissionsVM.getId());
        permissionsService.create(vm2);
        PermissionsVM vm3 = new PermissionsVM();
        vm3.setName(DEFAULT_NAME + "B");
        vm3.setPid(newPermissionsVM.getId());
        permissionsService.create(vm3);
        newPermissionsVM.setName(UPDATE_NAME);
        newPermissionsVM.setPid(UPDATE_PID);
        ResultActions actions = mvc.perform(put("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPermissionsVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void delete() throws Exception {
        PermissionsVM vm1 = new PermissionsVM();
        vm1.setName(DEFAULT_NAME);
        vm1.setPid(DEFAULT_PID);
        PermissionsVM newPermissionsVM = permissionsService.create(vm1);
        PermissionsVM vm2 = new PermissionsVM();
        vm2.setName(DEFAULT_NAME + "A");
        vm2.setPid(newPermissionsVM.getId());
        permissionsService.create(vm2);
        PermissionsVM vm3 = new PermissionsVM();
        vm3.setName(DEFAULT_NAME + "B");
        vm3.setPid(newPermissionsVM.getId());
        permissionsService.create(vm3);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/permissions/" + newPermissionsVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
    }
}
