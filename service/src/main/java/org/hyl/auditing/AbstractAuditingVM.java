package org.hyl.auditing;

import org.hyl.config.Constants;

public class AbstractAuditingVM {

    private String lastModifiedBy;

    private String lastModifiedDate_zh;

    private Byte state = Constants.DATA_NORMAL_STATE;

    private String state_zh;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate_zh() {
        return lastModifiedDate_zh;
    }

    public void setLastModifiedDate_zh(String lastModifiedDate_zh) {
        this.lastModifiedDate_zh = lastModifiedDate_zh;
    }

    public String getState_zh() {
        state_zh = Constants.DATA_NORMAL_STATE_ZH;
        if (Constants.DATA_DELETE_STATE.equals(state)) {
            state_zh = Constants.DATA_DELETE_STATE_ZH;
        }
        return state_zh;
    }

    public void setState_zh(String state_zh) {
        this.state_zh = state_zh;
    }
}
