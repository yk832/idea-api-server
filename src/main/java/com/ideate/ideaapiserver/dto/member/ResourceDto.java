package com.ideate.ideaapiserver.dto.member;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto {

    private String path;

    private String originalName;

    private String fakeName;

}
