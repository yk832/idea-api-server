package com.ideate.ideaapiserver.config;

import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.entity.MemberStatusHistory;
import com.ideate.ideaapiserver.repository.MemberRepository;
import com.ideate.ideaapiserver.repository.MemberStatusRepository;
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

    private final MemberStatusRepository statusRepository;

    private static final Set<MemberStatus> MEMBER_STATUSES = Collections.unmodifiableSet(EnumSet.of(MemberStatus.NORMAL, MemberStatus.VIP));

    @Transactional
//    @Scheduled(fixedDelay = 40000)
    @Scheduled(cron = "0 0 0 * * *")
    public void updateMemberStatus() {
        // 마지막 접속일이 오늘이거나 오늘 이전인 고객목록 조회
        List<MemberStatusHistory> memberStatusHistories = statusRepository.findMemberStatusHistoriesByLastLoginDateLessThanEqual(LocalDateTime.now());

        // 마지막 접속일이 3일이 지나고, 휴면/삭제상태가 아닌 고객목록 필터링
        List<MemberStatusHistory> updateHistories = memberStatusHistories.stream()
                .filter(m -> MemberScheduler.isThreeDaysPassed(m.getLastLoginDate(), LocalDateTime.now()))
                .filter(m -> MEMBER_STATUSES.contains(m.getMemberStatus()))
            .collect(Collectors.toList());

        // 휴면상태로 변경
        updateHistories.forEach(m -> m.updateStatus(MemberStatus.INACTIVE));

        statusRepository.saveAll(updateHistories);
    }

    @Transactional
//    @Scheduled(fixedDelay = 60000)
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteMember() {
        List<MemberStatusHistory> deletedStatusHistories = statusRepository.findMemberStatusHistoriesByMemberStatus(MemberStatus.DELETED);

        List<String> memebersToDeleteList = deletedStatusHistories.stream()
                .map(MemberStatusHistory::getUid)
            .collect(Collectors.toList());

        memberRepository.deleteByUidIn(memebersToDeleteList);
    }

    public static boolean isThreeDaysPassed(LocalDateTime savedDateTime, LocalDateTime currentDateTime) {
        long daysDifference = ChronoUnit.DAYS.between(savedDateTime, currentDateTime);
        return daysDifference == 3;
    }

}
