package com.ideate.ideaapiserver.handler;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class LoginFailException extends RuntimeException {

    private final String errorCode;

    private final Integer status;

    public LoginFailException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.toString();
        this.status = errorCode.getStatus();
    }

    public LoginFailException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode.toString();
        this.status = errorCode.getStatus();
    }
}
