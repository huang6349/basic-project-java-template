package org.hyl.data.auditing;

import org.hyl.data.config.DataConstants;

public class AbstractAuditingVM {

    private Byte state = DataConstants.DATA_NORMAL_STATE;

    private String state_zh;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getState_zh() {
        state_zh = DataConstants.DATA_NORMAL_STATE_ZH;
        if (DataConstants.DATA_DELETE_STATE.equals(state)) {
            state_zh = DataConstants.DATA_DELETE_STATE_ZH;
        }
        return state_zh;
    }

    public void setState_zh(String state_zh) {
        this.state_zh = state_zh;
    }
}
