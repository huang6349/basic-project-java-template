package org.hyl.modules.file.service.mapper;

import org.hyl.modules.file.domain.File;
import org.hyl.modules.file.domain.vm.FileVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileMapper {

    public List<FileVM> adapt(List<File> files) {
        return files.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public FileVM adapt(File file) {
        return FileVM.adapt(file);
    }
}
