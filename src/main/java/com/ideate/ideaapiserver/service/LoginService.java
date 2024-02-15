package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.config.constant.HistoryType;
import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.dto.member.LoginRequest;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.MemberHistory;
import com.ideate.ideaapiserver.handler.LoginFailException;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
import com.ideate.ideaapiserver.repository.MemberRepository;
import com.ideate.ideaapiserver.util.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final MemberHistoryRepository memberHistoryRepository;

    @Transactional
    public Long login(LoginRequest request) {
        Member member = memberRepository.findByUid(request.getUid())
                .orElseThrow(() -> new LoginFailException(ErrorCode.NOT_FOUND_MEMBER));

        if (!SHA256.validatePassword(request.getPassword(),member.getPassword())) {
            throw new LoginFailException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        memberHistoryRepository.save(MemberHistory.create(member, HistoryType.LOGIN));

        Long count = memberHistoryRepository.getLoginSuccessCount(member.getUid(), HistoryType.LOGIN);
        if (count >= 10) {
            member.updateStatus(MemberStatus.VIP);
        }

        return member.getId();
    }

}
