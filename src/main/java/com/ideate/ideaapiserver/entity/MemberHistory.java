package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.config.HistoryType;
import com.ideate.ideaapiserver.config.MemberEntityListener;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

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

    private String nickname;

    private String birthday;

    private String mdn;

    // TODO image 관련 필드 @Embedded 변경하기 (Resource entity)
    private String imgPath;

    private String fakeImgName;

    private String originalImgName;

    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    public static MemberHistory create(Member member, HistoryType historyType) {
        Resource resource = member.getResource();
        return MemberHistory.builder()
                .uid(member.getUid())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .mdn(member.getMdn())
                .imgPath(Objects.nonNull(resource) ? resource.getPath() : null)
                .fakeImgName(Objects.nonNull(resource) ? resource.getFakeName() : null)
                .originalImgName(Objects.nonNull(resource) ? resource.getOriginalName() : null)
                .historyType(historyType)
            .build();
    }
}

