package org.hyl.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyl.domain.Authority;
import org.hyl.repository.AuthorityRepository;
import org.hyl.web.rest.vm.AuthorityVM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
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

    private static final String DEFAULT_ID = "ROLE_TEST";
    private static final String DEFAULT_NAME = "测试角色";
    private static final String UPDATE_NAME = "修改测试角色";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void create() throws Exception {
        AuthorityVM vm = new AuthorityVM();
        vm.setId(DEFAULT_ID);
        vm.setName(DEFAULT_NAME);
        ResultActions actions = mvc.perform(post("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void query() throws Exception {
        Authority authority = new Authority();
        authority.setId(DEFAULT_ID);
        authority.setName(DEFAULT_NAME);
        authorityRepository.save(authority);
        ResultActions actions = mvc.perform(get("/api/authority")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        Authority authority = new Authority();
        authority.setId(DEFAULT_ID);
        authority.setName(DEFAULT_NAME);
        Authority newAuthority = authorityRepository.save(authority);
        ResultActions actions = mvc.perform(get("/api/authority/" + newAuthority.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        Authority authority = new Authority();
        authority.setId(DEFAULT_ID);
        authority.setName(DEFAULT_NAME);
        authorityRepository.save(authority);
        ResultActions actions = mvc.perform(get("/api/authority/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        Authority authority = new Authority();
        authority.setId(DEFAULT_ID);
        authority.setName(DEFAULT_NAME);
        Authority newAuthority = authorityRepository.save(authority);
        AuthorityVM vm = new AuthorityVM();
        BeanUtils.copyProperties(newAuthority, vm);
        vm.setName(UPDATE_NAME);
        ResultActions actions = mvc.perform(put("/api/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void delete() throws Exception {
        Authority authority = new Authority();
        authority.setId(DEFAULT_ID);
        authority.setName(DEFAULT_NAME);
        Authority newAuthority = authorityRepository.save(authority);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/authority/" + newAuthority.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }
}
