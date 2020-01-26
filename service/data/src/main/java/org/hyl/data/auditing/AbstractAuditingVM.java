package org.hyl.data.auditing;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hyl.data.config.DataConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据审计视图模型")
public class AbstractAuditingVM {

    @ApiModelProperty("数据状态")
    private Byte state = DataConstants.DATA_NORMAL_STATE;

    @ApiModelProperty("数据状态（文字）")
    private String state_text;

    public String getState_text() {
        state_text = DataConstants.DATA_NORMAL_STATE_TEXT;
        if (DataConstants.DATA_DELETE_STATE.equals(state)) {
            state_text = DataConstants.DATA_DELETE_STATE_TEXT;
        }
        if (DataConstants.DATA_KEEP_STATE.equals(state)) {
            state_text = DataConstants.DATA_KEEP_STATE_TEXT;
        }
        if (DataConstants.DATA_DISABLED_STATE.equals(state)) {
            state_text = DataConstants.DATA_DISABLED_STATE_TEXT;
        }
        return state_text;
    }
}
