package com.ideate.ideaapiserver.config;

import com.ideate.ideaapiserver.config.constant.HistoryType;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
import com.ideate.ideaapiserver.util.BeanUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor
public class MemberEntityListener {


    @PreUpdate
    @PrePersist
    public void prePersistAndUpdate(final Member member) {
        saveMemberHistory(member);
    }

    @PreRemove
    public void delete(final Member member) {
//        saveMemberHistory(member, HistoryType.DELETE);
    }

    private static void saveMemberHistory(Member member) {
        MemberHistoryRepository memberHistoryRepository = BeanUtil.getBean(MemberHistoryRepository.class);
        MemberHistory memberHistory = MemberHistory.create(member);
        memberHistoryRepository.save(memberHistory);
    }

}
