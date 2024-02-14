package com.ideate.ideaapiserver.dto.member;

import com.ideate.ideaapiserver.entity.Resource;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {

    private Long id;

    private String path;

    private String originalName;

    private String fakeName;

    public static ResourceDto of(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .path(resource.getPath())
                .originalName(resource.getOriginalName())
                .fakeName(resource.getFakeName())
            .build();
    }

}
