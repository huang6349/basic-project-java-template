package org.hyl.system.web.rest.vm;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.File;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("文件视图模型")
public class FileVM extends AbstractIdAuditingVM {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private String size;

    @ApiModelProperty("下载次数")
    private Long num = 0L;

    @ApiModelProperty("下载地址")
    private String download;

    @ApiModelProperty("文件创建时间")
    private String createdBy_text;

    public static FileVM adapt(File file) {
        FileVM vm = new FileVM();
        BeanUtils.copyProperties(file, vm);
        vm.setType(StrUtil.subAfter(file.getName(), ".", true));
        vm.setDownload(StrUtil.format("/api/file/download/{}", file.getId()));
        if (file.getCreatedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(file.getCreatedDate(), ZoneId.systemDefault());
            vm.setCreatedBy_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }
}
