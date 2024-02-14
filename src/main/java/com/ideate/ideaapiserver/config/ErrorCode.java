package com.ideate.ideaapiserver.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    LOGIN_FAIL(401,"아이디/비밀번호가 틀렸습니다."),
    NOT_FOUND(404,"PAGE NOT FOUND"),
    NOT_FOUND_MEMBER(404,"존재하지 않는 고객"),
    INTERNAL_SERVER_ERROR(500,"서버에 장애가 발생하였습니다.\n관리자에게 문의해 주세요."),
    NOT_FOUND_RESOURCE(404, "존재하지 않는 이미지 리소스");

    private final Integer status;

    private final String message;

}