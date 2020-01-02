package org.hyl.system.web.rest.vm;

import com.google.common.collect.Sets;

import java.util.Set;

public class UpdateAuthorityPermissionsVM {

    private Long id;

    private Set<Long> permissions = Sets.newHashSet();

    public UpdateAuthorityPermissionsVM() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Long> permissions) {
        this.permissions = permissions;
    }
}
