package com.ideate.ideaapiserver.controller;


import com.ideate.ideaapiserver.dto.memberhistory.MemberHistoryDto;
import com.ideate.ideaapiserver.service.MemberHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final MemberHistoryService memberHistoryService;

    @GetMapping("/api/histories/member")
    public ResponseEntity<List<MemberHistoryDto>> findAll() {
        return ResponseEntity.ok(memberHistoryService.findAll());
    }

    @GetMapping("/api/histories/member/{uid}")
    public ResponseEntity<List<MemberHistoryDto>> findById(@PathVariable Long uid) {
        return ResponseEntity.ok(memberHistoryService.findById(uid));
    }

}
