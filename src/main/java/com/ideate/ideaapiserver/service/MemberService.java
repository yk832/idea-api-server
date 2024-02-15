package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.dto.resource.ResourceDto;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.Resource;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final ResourceService resourceService;

    private final MemberRepository memberRepository;

    @Transactional
    public Long create(MultipartFile image, MemberDto.Create request) {
        Member member = Member.create(request);

        if (Objects.isNull(image)) {
            memberRepository.save(member);
            return member.getId();
        }

        Resource resource = getResource(image);

        member = memberRepository.save(Member.create(request, resource));

        return member.getId();
    }

    public List<MemberDto.Response> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberDto.Response::getUidAndNameAndMdn)
                .toList();
    }

    public MemberDto.Response findById(Long id) {
        return memberRepository.findById(id)
                .map(MemberDto.Response::of)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Transactional
    public void update(Long id, MultipartFile image, MemberDto.Update request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));

        if (Objects.isNull(image)) {
            member.update(request);
            return;
        }

        Resource resource = getResource(image);

        member.update(request, resource);

    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));

        memberRepository.delete(member);

    }

    private Resource getResource(MultipartFile image) {
        ResourceDto resourceDto = resourceService.uploadImgFile(image);
        return Resource.create(resourceDto);
    }

}
