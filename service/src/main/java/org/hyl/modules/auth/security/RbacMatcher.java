package org.hyl.modules.auth.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RbacMatcher {

    private String pattern;

    private String method;
}
