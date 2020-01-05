package org.hyl.data.auditing;

import org.hyl.data.config.DataConstants;

public class AbstractAuditingVM {

    private Byte state = DataConstants.DATA_NORMAL_STATE;

    private String state_text;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getState_text() {
        state_text = DataConstants.DATA_NORMAL_STATE_TEXT;
        if (DataConstants.DATA_DELETE_STATE.equals(state)) {
            state_text = DataConstants.DATA_DELETE_STATE_TEXT;
        }
        if (DataConstants.DATA_KEEP_STATE.equals(state)) {
            state_text = DataConstants.DATA_KEEP_STATE_TEXT;
        }
        return state_text;
    }

    public void setState_text(String state_text) {
        this.state_text = state_text;
    }
}
