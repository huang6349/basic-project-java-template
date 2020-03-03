package org.hyl.file.web.rest;

import com.github.wenhao.jpa.Specifications;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hyl.commons.result.Message;
import org.hyl.commons.result.PaginationUtil;
import org.hyl.commons.result.RESTful;
import org.hyl.commons.result.enums.RestTypeEnum;
import org.hyl.file.domain.File;
import org.hyl.file.repository.FileRepository;
import org.hyl.file.service.FileService;
import org.hyl.file.web.rest.vm.FileVM;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/api")
public class FileResource {

    private final FileRepository fileRepository;

    private final FileService fileService;

    public FileResource(FileRepository fileRepository, FileService fileService) {
        this.fileRepository = fileRepository;
        this.fileService = fileService;
    }

    @ApiOperation("查询所有的文件")
    @GetMapping("/file")
    public Message query() {
        return RESTful.success(RestTypeEnum.GET, fileRepository.findAll().stream().map(FileVM::adapt).collect(Collectors.toList()));
    }

    @ApiOperation("查询一个文件")
    @GetMapping("/file/{id}")
    public Message query(@PathVariable Long id) {
        Optional<File> optional = fileRepository.findById(id);
        return RESTful.success(RestTypeEnum.GET, optional.map(FileVM::adapt).orElse(null));
    }

    @ApiOperation("分页查询文件")
    @GetMapping("/file/pageable")
    public Message query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String name) {
        Specification<File> specification = Specifications.<File>and()
                .like(StringUtils.isNotBlank(name), "name", "%" + StringUtils.trim(name) + "%")
                .build();
        return PaginationUtil.execute(fileRepository.findAll(specification, pageable).map(FileVM::adapt));
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Message local(@RequestParam("file") MultipartFile file) {
        return RESTful.success(RestTypeEnum.POST, "上传成功", fileService.create(file));
    }

    @ApiOperation("下载文件")
    @GetMapping("/file/download/{id}")
    public void local(HttpServletResponse response, @PathVariable Long id) {
        fileService.download(response, id);
    }
}
