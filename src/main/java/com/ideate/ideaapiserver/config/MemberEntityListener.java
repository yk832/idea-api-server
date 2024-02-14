package com.ideate.ideaapiserver.config;

import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
import com.ideate.ideaapiserver.util.BeanUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MemberEntityListener {

    @PreUpdate
    @PrePersist
    public void prePersist(final Member member) {
        MemberHistoryRepository memberHistoryRepository = BeanUtil.getBean(MemberHistoryRepository.class);
        HistoryType historyType = member.getId() == null ? HistoryType.CREATE : HistoryType.UPDATE;
        MemberHistory memberHistory = MemberHistory.create(member, historyType);
        memberHistoryRepository.save(memberHistory);
    }

}
