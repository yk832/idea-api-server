package com.ideate.ideaapiserver.config.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // TODO status, msg 정리
    PASSWORD_NOT_MATCH(401,"비밀번호가 틀렸습니다."),
    NOT_FOUND(404,"PAGE NOT FOUND"),
    NOT_FOUND_MEMBER(404,"존재하지 않는 고객"),
    INTERNAL_SERVER_ERROR(500,"서버에 장애가 발생하였습니다.\n관리자에게 문의해 주세요."),
    NOT_FOUND_RESOURCE(404, "존재하지 않는 이미지 리소스"),
    FILE_UPLOAD_FAIL(500, "파일 업로드 실패"),
    FILE_DOWNLOAD_FAIL(500, "파일 다운로드 실패");

    private final Integer status;

    private final String message;

}