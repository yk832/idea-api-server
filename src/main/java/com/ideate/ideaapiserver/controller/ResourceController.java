package com.ideate.ideaapiserver.controller;


import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.service.MemberService;
import com.ideate.ideaapiserver.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <pre>
 * ${package_name}
 * ${ResourceController}
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
public class ResourceController {

    private final ResourceService resourceService;


    @GetMapping("/api/resources")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok("");
    }

//    @GetMapping("/api/members/{id}")
//    public ResponseEntity<MemberDto.Response> findById(@PathVariable Long id) {
//        return ResponseEntity.ok(memberService.findById(id));
//    }
//
//    @PostMapping("/api/members")
//    public ResponseEntity<Long> createMember(@RequestPart(required = false) MultipartFile image, @RequestPart @Valid MemberDto.Create request) {
//        return ResponseEntity.ok(memberService.create(image, request));
//    }
//
//    @PutMapping("/api/members/{id}")
//    public ResponseEntity<Long> updateMember(@PathVariable Long id,
//               @RequestPart(required = false) MultipartFile image, @RequestPart @Valid MemberDto.Update request) {
//        return ResponseEntity.ok(memberService.update(id, image, request));
//    }
//
    @DeleteMapping("/api/resources/{id}")
    public ResponseEntity<Long> deleteResource(@PathVariable Long id) {
        return ResponseEntity.ok(1L);
    }


}
