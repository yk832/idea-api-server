package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.config.constant.HistoryType;
import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.dto.resource.ResourceInfo;
import com.ideate.ideaapiserver.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "MEMBER_HISTORYS")
@Getter
@ToString
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = true)
public class MemberHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String mdn;

    @Embedded
    ResourceInfo resourceInfo;

    public static MemberHistory create(Member member) {
        Resource resource = member.getResource();
        return MemberHistory.builder()
                .uid(member.getUid())
                .password(member.getPassword())
                .name(member.getName())
                .mdn(member.getMdn())
                .resourceInfo(Objects.nonNull(resource) ? resource.getResourceInfo() : null)
            .build();
    }
}

