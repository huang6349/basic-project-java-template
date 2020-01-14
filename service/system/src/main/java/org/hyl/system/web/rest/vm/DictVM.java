package org.hyl.system.web.rest.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hyl.data.auditing.AbstractIdAuditingVM;
import org.hyl.system.config.Constants;
import org.hyl.system.domain.Dict;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ApiModel("字典视图模型")
public class DictVM extends AbstractIdAuditingVM {

    @ApiModelProperty("上级数据编号")
    private Long pid = 0L;

    @ApiModelProperty("字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称的长度只能小于50个字符")
    private String name;

    @ApiModelProperty("字典唯一标识码")
    @Size(max = 50, message = "字典唯一标识码的长度只能小于50个字符")
    private String code;

    @ApiModelProperty("字典数据")
    @Size(max = 50, message = "字典数据的长度只能小于50个字符")
    private String data;

    @ApiModelProperty("字典描述")
    private String desc;

    @ApiModelProperty("字典信息最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty("字典信息最后修改时间")
    private String lastModifiedDate_text;

    public static DictVM adapt(Dict dict) {
        DictVM vm = new DictVM();
        BeanUtils.copyProperties(dict, vm);
        if (dict.getLastModifiedDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dict.getLastModifiedDate(), ZoneId.systemDefault());
            vm.setLastModifiedDate_text(localDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMATTER)));
        }
        return vm;
    }

    public DictVM() {
        // Empty constructor needed for Jackson.
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate_text() {
        return lastModifiedDate_text;
    }

    public void setLastModifiedDate_text(String lastModifiedDate_text) {
        this.lastModifiedDate_text = lastModifiedDate_text;
    }
}
