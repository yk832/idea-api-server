package com.ideate.ideaapiserver.dto.member;

import com.ideate.ideaapiserver.config.HistoryType;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.entity.Resource;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberHistoryDto {

    private Long id;

    private String password;

    private String name;

    private String mdn;

    // TODO image 관련 필드 @Embedded 변경하기 (Resource entity)
    private String imgPath;

    private String fakeImgName;

    private String originalImgName;

    private HistoryType historyType;


    public static MemberHistoryDto of(MemberHistory history) {
        return MemberHistoryDto.builder()
                .id(history.getId())
                .password(history.getPassword())
                .name(history.getName())
                .mdn(history.getMdn())
                .imgPath(history.getImgPath())
                .fakeImgName(history.getFakeImgName())
                .originalImgName(history.getOriginalImgName())
                .historyType(history.getHistoryType())
            .build();
    }
}
