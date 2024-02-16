package com.ideate.ideaapiserver.dto.memberhistory;

import com.ideate.ideaapiserver.config.constant.HistoryType;
import com.ideate.ideaapiserver.dto.resource.ResourceInfo;
import com.ideate.ideaapiserver.entity.MemberHistory;
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

    private ResourceInfo resourceInfo;

    private HistoryType historyType;


    public static MemberHistoryDto of(MemberHistory history) {
        return MemberHistoryDto.builder()
                .id(history.getId())
                .password(history.getPassword())
                .name(history.getName())
                .mdn(history.getMdn())
                .resourceInfo(history.getResourceInfo())
                .historyType(history.getHistoryType())
            .build();
    }
}
