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
                .map(m -> validateMemberStatus(request.getPassword(), m))
                .orElseThrow(() -> new LoginFailException(ErrorCode.NOT_FOUND_MEMBER));

        loginHistoryRepository.save(LoginHistory.create(member));

        Optional<MemberStatusHistory> findMemberStatusHistory = memberStatusRepository.findByUid(member.getUid());
        if (findMemberStatusHistory.isEmpty()) {
            memberStatusRepository.save(MemberStatusHistory.create(member));
            return member.getId();
        }

        MemberStatusHistory statusHistory = findMemberStatusHistory.get();
        statusHistory.incrementLoginCount(statusHistory.getLoginCount() + 1);

        if(statusHistory.getLoginCount() == 10) {
            member.updateStatus(MemberStatus.VIP);
            statusHistory.updateStatus(MemberStatus.VIP);
        }

        memberStatusRepository.save(statusHistory);

        return member.getId();
    }

    private Member validateMemberStatus(String requestPassword, Member member) {
        if (!SHA256.validatePassword(requestPassword, member.getPassword())) {
            throw new LoginFailException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        if (member.getMemberStatus().equals(MemberStatus.DELETED)) {
            throw new LoginFailException(ErrorCode.NOT_FOUND_MEMBER);
        } else if (member.getMemberStatus().equals(MemberStatus.INACTIVE)) {
            throw new LoginFailException(ErrorCode.DISABLED_MEMBER);
        }

        return member;
    }

}
