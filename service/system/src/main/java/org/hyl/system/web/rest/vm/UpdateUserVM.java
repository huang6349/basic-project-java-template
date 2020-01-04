package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;
import org.hyl.data.auditing.AbstractIdAuditingVM;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class UpdateUserVM extends AbstractIdAuditingVM {

    @NotEmpty(message = "用户角色不能为空")
    private Set<Long> authorities = Sets.newHashSet();

    public Set<Long> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Long> authorities) {
        this.authorities = authorities;
    }
}
