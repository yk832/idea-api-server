package com.ideate.ideaapiserver.config;

import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
import com.ideate.ideaapiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberRepository memberRepository;

    private final MemberHistoryRepository memberHistoryRepository;
    private static final Set<MemberStatus> MEMBER_STATUSES = Collections.unmodifiableSet(EnumSet.of(MemberStatus.NORMAL, MemberStatus.VIP));

    @Transactional
//    @Scheduled(fixedDelay = 40000)
    @Scheduled(cron = "0 0 0 * * *")
    public void updateMemberStatus() {
        List<String> updateMembers = memberHistoryRepository.findByLoginHistoryType().stream()
                .filter(m -> isThreeDaysPassed(m.getCreatedAt(), LocalDateTime.now()))
                .filter(m -> MEMBER_STATUSES.contains(m.getMemberStatus()))
                .map(MemberHistory::getUid).distinct()
                .collect(Collectors.toList());

        if(!updateMembers.isEmpty()) {
            memberRepository.findByUidIn(updateMembers).stream()
                    .filter(m -> MEMBER_STATUSES.contains(m.getMemberStatus()))
                    .forEach(member -> member.updateStatus(MemberStatus.INACTIVE));
        }
    }

    @Transactional
//    @Scheduled(fixedDelay = 60000)
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteMember() {
        List<String> deleteMembers = memberHistoryRepository.findByUpdateHistoryType().stream()
                .filter(m -> isThreeDaysPassed(m.getCreatedAt(), LocalDateTime.now()))
                .filter(m -> m.getMemberStatus().equals(MemberStatus.DELETED))
                .map(MemberHistory::getUid).distinct()
                .collect(Collectors.toList());

        if(!deleteMembers.isEmpty()) {
            memberRepository.deleteByUidIn(deleteMembers);
        }

    }

    public boolean isThreeDaysPassed(LocalDateTime databaseDateTime, LocalDateTime currentDateTime) {
        long daysDifference = ChronoUnit.DAYS.between(databaseDateTime, currentDateTime);
        return daysDifference == 3;
    }

}
