package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.dto.resource.ResourceDto;
import com.ideate.ideaapiserver.dto.resource.ResourceInfo;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.MemberStatusHistory;
import com.ideate.ideaapiserver.entity.Resource;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.MemberRepository;
import com.ideate.ideaapiserver.repository.MemberStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final ResourceService resourceService;

    private final MemberRepository memberRepository;

    private final MemberStatusRepository memberStatusRepository;


    @Transactional
    public Long create(MultipartFile image, MemberDto.Create request) {
        if (memberRepository.findByUid(request.getUid()).isPresent()) {
            throw new GlobalException(ErrorCode.REGISTERED_UID);
        }

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
                .map(MemberDto.Response::getSensitiveData)
                .toList();
    }

    public MemberDto.Response findById(Long id) {
        return memberRepository.findById(id)
                .map(MemberDto.Response::of)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Transactional
    public void update(Long id, MultipartFile image, MemberDto.Update request) {
        Member member = findMemberById(id);

        if (Objects.isNull(image)) {
            member.update(request);
            return;
        }

        Resource resource = getResource(image);

        member.update(request, resource);

    }

    @Transactional
    public void delete(Long id) {
        Member member = findMemberById(id);

        memberRepository.delete(member);
    }

    @Transactional
    public Long leave(Long id) {
        Member member = findMemberById(id);

        memberStatusRepository.findByUid(member.getUid()).ifPresent(m -> {
            m.updateStatus(MemberStatus.DELETED);
            memberStatusRepository.save(m);
        });

        return member.getId();
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_MEMBER));
    }

    private Resource getResource(MultipartFile image) {
        ResourceInfo resourceInfo = resourceService.uploadImgFile(image);
        return Resource.create(resourceInfo);
    }
}
