package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.config.HistoryType;
import com.ideate.ideaapiserver.config.MemberEntityListener;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "MEMBER_HISTORY")
@Getter
@ToString
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class MemberHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String nickname;

    private String birthday;

    private String mdn;

//    @OneToOne(fetch = FetchType.LAZY)
//    private Resource resource;

    @Enumerated(EnumType.STRING)
    private HistoryType historyType;

    public static MemberHistory create(Member member, HistoryType historyType) {
        return MemberHistory.builder()
                .uid(member.getUid())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .mdn(member.getMdn())
//                .resource(member.getResource())
                .historyType(historyType)
            .build();
    }
}

