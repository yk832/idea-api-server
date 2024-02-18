package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.dto.memberhistory.MemberHistoryDto;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
import com.ideate.ideaapiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberHistoryService {

    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;

    public List<MemberHistoryDto> findAll() {
        return memberHistoryRepository.findAll().stream()
                .map(MemberHistoryDto::of)
                .sorted(Comparator.comparing(MemberHistoryDto::getId).reversed())
            .collect(Collectors.toList());
    }

    public List<MemberHistoryDto> findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        return memberHistoryRepository.findAllByUid(member.getUid()).stream()
                .map(MemberHistoryDto::of)
                .sorted(Comparator.comparing(MemberHistoryDto::getId).reversed())
                .collect(Collectors.toList());
    }

}
