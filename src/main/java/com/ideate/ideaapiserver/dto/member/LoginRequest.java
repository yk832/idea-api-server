package com.ideate.ideaapiserver.dto.member;

import lombok.Data;

@Data
public class LoginRequest {

    private String uid;

    private String password;

}
