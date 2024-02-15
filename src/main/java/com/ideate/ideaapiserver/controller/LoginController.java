package com.ideate.ideaapiserver.controller;

import com.ideate.ideaapiserver.dto.common.CommonResponse;
import com.ideate.ideaapiserver.dto.member.LoginRequest;
import com.ideate.ideaapiserver.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/login")
    public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest request) {
        Long id = loginService.login(request);
        return ResponseEntity.ok(new CommonResponse(id));
    }

}
