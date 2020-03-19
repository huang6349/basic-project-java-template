package org.hyl.modules.auth.service.mapper;

import org.hyl.modules.auth.domain.Resource;
import org.hyl.modules.auth.domain.vm.ResourceVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResourceMapper {

    public List<ResourceVM> adapt(List<Resource> resources) {
        return resources.stream().filter(Objects::nonNull).map(this::adapt).collect(Collectors.toList());
    }

    public ResourceVM adapt(Resource resource) {
        return ResourceVM.adapt(resource);
    }
}
