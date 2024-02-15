package com.ideate.ideaapiserver.controller;


import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.service.MemberService;
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
public class MemberHistoryController {


    @GetMapping("/api/histories/member")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/api/histories/member/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok("");
    }

}
