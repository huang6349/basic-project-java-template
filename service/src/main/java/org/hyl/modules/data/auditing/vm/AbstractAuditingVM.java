package org.hyl.modules.data.auditing.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.config.GlobalConstants;

@ApiModel("数据审计视图模型")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractAuditingVM {

    @ApiModelProperty("数据状态")
    private Byte state = GlobalConstants.DATA_NORMAL_STATE;

    @ApiModelProperty("数据状态（文字）")
    private String state_text;

    public String getState_text() {
        state_text = GlobalConstants.DATA_NORMAL_STATE_TEXT;
        if (GlobalConstants.DATA_DELETE_STATE.equals(state)) {
            state_text = GlobalConstants.DATA_DELETE_STATE_TEXT;
        }
        if (GlobalConstants.DATA_KEEP_STATE.equals(state)) {
            state_text = GlobalConstants.DATA_KEEP_STATE_TEXT;
        }
        if (GlobalConstants.DATA_DISABLED_STATE.equals(state)) {
            state_text = GlobalConstants.DATA_DISABLED_STATE_TEXT;
        }
        return state_text;
    }
}
