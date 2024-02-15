package com.ideate.ideaapiserver.controller;


import com.ideate.ideaapiserver.dto.common.CommonResponse;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.service.MemberService;
import com.ideate.ideaapiserver.service.ResourceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * ${package_name}
 * ${MemberController}
 * </pre>
 *
 * @author : ${user}
 * @date : ${date} ${time}
 * @desc :
 * @version : 0.1
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/api/members")
    public ResponseEntity<List<MemberDto.Response>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/api/members/{id}")
    public ResponseEntity<MemberDto.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PostMapping("/api/members")
    public ResponseEntity<CommonResponse> createMember(@RequestPart(required = false) MultipartFile image, @RequestPart @Valid MemberDto.Create request) {
        Long id = memberService.create(image, request);
        return ResponseEntity.ok(new CommonResponse(id));
    }

    @PutMapping("/api/members/{id}")
    public ResponseEntity<CommonResponse> updateMember(@PathVariable Long id,
               @RequestPart(required = false) MultipartFile image, @RequestPart @Valid MemberDto.Update request) {
        // TODO parameter code convention
        memberService.update(id, image, request);
        return ResponseEntity.ok(new CommonResponse(id));
    }

    @DeleteMapping("/api/members/{id}")
    public ResponseEntity<CommonResponse> deleteMember(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(new CommonResponse(id));
    }
}
