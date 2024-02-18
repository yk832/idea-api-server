package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.config.constant.MemberStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Builder
@Document(collection = "member_status_histories")
public class MemberStatusHistory {

    @Id
    private String id;

    private String uid;

    private Long loginCount;

    private MemberStatus memberStatus;

    private LocalDateTime firstLoginDate;

    private LocalDateTime lastLoginDate;


    public static MemberStatusHistory create(Member member) {
        return MemberStatusHistory.builder()
                .uid(member.getUid())
                .loginCount(1L)
                .memberStatus(MemberStatus.NORMAL)
                .firstLoginDate(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
            .build();
    }

    public static MemberStatusHistory create(Member member, Long loginCount) {
        return MemberStatusHistory.builder()
                .uid(member.getUid())
                .loginCount(loginCount)
                .memberStatus(MemberStatus.NORMAL)
                .lastLoginDate(LocalDateTime.now())
            .build();
    }

    public void updateStatus(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Long incrementLoginCount(Long loginCount) {
        this.loginCount = loginCount;
        return loginCount;
    }
}
