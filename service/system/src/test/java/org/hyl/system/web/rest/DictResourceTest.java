package org.hyl.system.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.hyl.data.auditing.LevelUtil;
import org.hyl.system.domain.Dict;
import org.hyl.system.repository.DictRepository;
import org.hyl.system.service.DictService;
import org.hyl.system.web.rest.vm.DictVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class DictResourceTest {

    private static final String DEFAULT_NAME = "测试字典";
    private static final String DEFAULT_CODE = "测试字典唯一标识码";
    private static final String DEFAULT_DATA = "测试字典数据";
    private static final String UPDATE_NAME = "修改测试字典";
    private static final String UPDATE_DATA = "修改测试字典数据";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DictRepository dictRepository;

    @Autowired
    private DictService dictService;

    private DictVM vm;

    @Before
    public void setup() {
        vm = new DictVM();
        vm.setName(DEFAULT_NAME);
        vm.setCode(DEFAULT_CODE);
    }

    @Test
    public void create() throws Exception {
        List<Dict> prevAll = dictRepository.findAll();
        ResultActions actions = mvc.perform(post("/api/dict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vm)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() + 1);
        Dict dict = currAll.get(currAll.size() - 1);
        Assertions.assertThat(dict.getId()).isNotNull();
        Assertions.assertThat(dict.getPid()).isEqualTo(0);
        Assertions.assertThat(dict.getLevel()).isEqualTo(StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
        Assertions.assertThat(dict.getName()).isEqualTo(DEFAULT_NAME);
        Assertions.assertThat(dict.getCode()).isEqualTo(DEFAULT_CODE);
        Assertions.assertThat(dict.getData()).isNull();
        Assertions.assertThat(dict.getDesc()).isNull();
        Assertions.assertThat(dict.getCreatedBy()).isNotNull();
        Assertions.assertThat(dict.getCreatedDate()).isNotNull();
        Assertions.assertThat(dict.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(dict.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void query() throws Exception {
        dictService.create(vm);
        ResultActions actions = mvc.perform(get("/api/dict")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryById() throws Exception {
        DictVM dictVM = dictService.create(vm);
        ResultActions actions = mvc.perform(get("/api/dict/" + dictVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryByPageable() throws Exception {
        dictService.create(vm);
        ResultActions actions = mvc.perform(get("/api/dict/pageable")
                .param("page", "0")
                .param("size", "20")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void queryToTree() throws Exception {
        DictVM dictVM = dictService.create(vm);
        DictVM vm1 = new DictVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setData(DEFAULT_DATA + "A");
        vm1.setPid(dictVM.getId());
        dictService.create(vm1);
        DictVM vm2 = new DictVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setData(DEFAULT_DATA + "B");
        vm2.setPid(dictVM.getId());
        dictService.create(vm2);
        ResultActions actions = mvc.perform(get("/api/dict/tree")
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void update() throws Exception {
        DictVM parentDictVM = new DictVM();
        parentDictVM.setName("父级测试字典");
        parentDictVM.setCode("父级测试字典唯一标识码");
        DictVM newParentDictVM = dictService.create(parentDictVM);
        DictVM dictVM = dictService.create(vm);
        dictVM.setName(UPDATE_NAME);
        dictVM.setCode(null);
        dictVM.setData(UPDATE_DATA);
        dictVM.setPid(newParentDictVM.getId());
        List<Dict> prevAll = dictRepository.findAll();
        Dict prevDict = prevAll.get(prevAll.size() - 1);
        ResultActions actions = mvc.perform(put("/api/dict")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dictVM)));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size());
        Dict currDict = currAll.get(currAll.size() - 1);
        Assertions.assertThat(currDict.getId()).isEqualTo(prevDict.getId());
        Assertions.assertThat(currDict.getPid()).isEqualTo(newParentDictVM.getId());
        Assertions.assertThat(currDict.getLevel()).isEqualTo(LevelUtil.calculateLevel("0E", newParentDictVM.getId()));
        Assertions.assertThat(currDict.getName()).isEqualTo(UPDATE_NAME);
        Assertions.assertThat(currDict.getCode()).isNull();
        Assertions.assertThat(currDict.getData()).isEqualTo(UPDATE_DATA);
        Assertions.assertThat(currDict.getDesc()).isEqualTo(prevDict.getDesc());
        Assertions.assertThat(currDict.getCreatedBy()).isEqualTo(prevDict.getCreatedBy());
        Assertions.assertThat(currDict.getCreatedDate()).isEqualTo(prevDict.getCreatedDate());
        Assertions.assertThat(currDict.getLastModifiedBy()).isNotNull();
        Assertions.assertThat(currDict.getLastModifiedDate()).isNotNull();
    }

    @Test
    public void delete() throws Exception {
        DictVM dictVM = dictService.create(vm);
        DictVM vm1 = new DictVM();
        vm1.setName(DEFAULT_NAME + "A");
        vm1.setData(DEFAULT_DATA + "A");
        vm1.setPid(dictVM.getId());
        dictService.create(vm1);
        DictVM vm2 = new DictVM();
        vm2.setName(DEFAULT_NAME + "B");
        vm2.setData(DEFAULT_DATA + "B");
        vm2.setPid(dictVM.getId());
        dictService.create(vm2);
        List<Dict> prevAll = dictRepository.findAll();
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/api/dict/" + dictVM.getId())
                .accept(MediaType.APPLICATION_JSON));
        actions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        actions.andExpect(status().isOk()).andDo(print());
        List<Dict> currAll = dictRepository.findAll();
        Assertions.assertThat(currAll).hasSize(prevAll.size() - 3);
    }
}
