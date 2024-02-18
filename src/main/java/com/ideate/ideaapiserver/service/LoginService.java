package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.dto.member.LoginRequest;
import com.ideate.ideaapiserver.entity.LoginHistory;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.MemberStatusHistory;
import com.ideate.ideaapiserver.handler.LoginFailException;
import com.ideate.ideaapiserver.repository.LoginHistoryRepository;
import com.ideate.ideaapiserver.repository.MemberRepository;
import com.ideate.ideaapiserver.repository.MemberStatusRepository;
import com.ideate.ideaapiserver.util.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    private final MemberStatusRepository memberStatusRepository;

    @Transactional
    public Long login(LoginRequest request) {
        Member member = memberRepository.findByUid(request.getUid())
                .orElseThrow(() -> new LoginFailException(ErrorCode.NOT_FOUND_MEMBER));

        if (!SHA256.validatePassword(request.getPassword(), member.getPassword())) {
            throw new LoginFailException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        Optional<MemberStatusHistory> findMemberStatusHistory = memberStatusRepository.findByUid(member.getUid());
        if (findMemberStatusHistory.isEmpty()) {
            memberStatusRepository.save(MemberStatusHistory.create(member));
            return member.getId();
        }

        MemberStatusHistory statusHistory = findMemberStatusHistory.get();

        // 삭제된 고객, 휴면 고객 검증
        validateMemberStatus(statusHistory.getMemberStatus());

        Long updateLoginCount = statusHistory.incrementLoginCount(statusHistory.getLoginCount() + 1);
        if(updateLoginCount == 10) {
            statusHistory.updateStatus(MemberStatus.VIP);
        }

        loginHistoryRepository.save(LoginHistory.create(member));

        memberStatusRepository.save(statusHistory);

        return member.getId();
    }

    private void validateMemberStatus(MemberStatus memberStatus) {
        if (memberStatus.equals(MemberStatus.DELETED)) {
            throw new LoginFailException(ErrorCode.NOT_FOUND_MEMBER);
        } else if (memberStatus.equals(MemberStatus.INACTIVE)) {
            throw new LoginFailException(ErrorCode.DISABLED_MEMBER);
        }
    }

}
