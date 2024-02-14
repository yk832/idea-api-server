package com.ideate.ideaapiserver.handler;

import com.ideate.ideaapiserver.config.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException {

    private final String errorCode;

    private final Integer status;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.toString();
        this.status = errorCode.getStatus();
    }

    public GlobalException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode.toString();
        this.status = errorCode.getStatus();
    }
}
