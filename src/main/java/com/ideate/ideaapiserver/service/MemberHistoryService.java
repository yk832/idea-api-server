package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.dto.memberhistory.MemberHistoryDto;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.MemberHistoryRepository;
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

    private final MemberHistoryRepository memberHistoryRepository;

    public List<MemberHistoryDto> findAll() {
        return memberHistoryRepository.findAll().stream()
                .map(MemberHistoryDto::of)
                .sorted(Comparator.comparing(MemberHistoryDto::getId).reversed())
            .collect(Collectors.toList());
    }

    public MemberHistoryDto findById(Long id) {
        return memberHistoryRepository.findById(id)
                .map(MemberHistoryDto::of)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
    }

}
