package org.hyl.system.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;
import org.hyl.data.config.DataConstants;
import org.hyl.system.domain.File;
import org.hyl.commons.exception.BadRequestException;
import org.hyl.system.repository.FileRepository;
import org.hyl.system.web.rest.vm.FileVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

@Service
@Transactional
public class FileService {

    private final MultipartProperties multipartProperties;

    private final FileRepository fileRepository;

    @Autowired
    public FileService(MultipartProperties multipartProperties, FileRepository fileRepository) {
        this.multipartProperties = multipartProperties;
        this.fileRepository = fileRepository;
    }

    public FileVM create(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new BadRequestException("上传失败，不允许上传空文件");
        }
        String fileName = multipartFile.getOriginalFilename();
        String rawFileName = StrUtil.subBefore(fileName, ".", true);
        String fileType = StrUtil.subAfter(fileName, ".", true);
        String localFilePath = StrUtil.format("{}{}{}{}-{}.{}", StrUtil.trimToEmpty(StrUtil.appendIfMissing(multipartProperties.getLocation(), "/")), StrUtil.appendIfMissing(DateUtil.today(), "/"), StrUtil.appendIfMissing(fileType, "/"), rawFileName, DateUtil.current(false), fileType);
        try {
            multipartFile.transferTo(FileUtil.touch(localFilePath));
        } catch (IOException e) {
            throw new BadRequestException("上传失败，服务器暂时无法处理这个文件");
        }
        File file = new File();
        file.setName(fileName);
        file.setUrl(localFilePath);
        file.setSize(StrUtil.format("{}KB", DataSize.ofBytes(multipartFile.getSize()).toKilobytes()));
        file.setState(DataConstants.DATA_NORMAL_STATE);
        return FileVM.adapt(fileRepository.save(file));
    }

    public void download(HttpServletResponse response, Long id) {
        Optional<File> optional = fileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException("未找到需要下载的文件信息");
        }
        try {
            File file = optional.get();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, StrUtil.format("attachment; fileName={}", URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20")));
            FileReader reader = new FileReader(file.getUrl());
            reader.writeToStream(response.getOutputStream());
            file.setNum(file.getNum() + 1);
            fileRepository.save(file);
        } catch (IORuntimeException e) {
            throw new BadRequestException("下载失败，这个文件已经被删除了");
        } catch (IOException e) {
            throw new BadRequestException("下载失败，服务器暂时无法处理这个文件");
        }
    }
}
