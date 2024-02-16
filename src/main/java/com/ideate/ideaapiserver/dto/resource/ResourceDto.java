package com.ideate.ideaapiserver.dto.resource;

import com.ideate.ideaapiserver.entity.Resource;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {

    private Long id;

    private ResourceInfo resourceInfo;

    public static ResourceDto of(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .resourceInfo(resource.getResourceInfo())
            .build();
    }

}
