package com.ideate.ideaapiserver.dto.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.entity.Resource;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ResourceInfo {

    private String imgPath;

    private String fakeImgName;

    private String originalImgName;

    public static ResourceInfo of(Resource resource) {
        return ResourceInfo.builder()
                .imgPath(resource.getResourceInfo().getImgPath())
                .fakeImgName(resource.getResourceInfo().getFakeImgName())
                .originalImgName(resource.getResourceInfo().getOriginalImgName())
            .build();
    }

}
