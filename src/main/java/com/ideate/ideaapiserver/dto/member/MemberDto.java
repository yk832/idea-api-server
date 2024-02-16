package com.ideate.ideaapiserver.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ideate.ideaapiserver.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.function.Function;

public class MemberDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        @NotBlank(message = "아이디는 필수 입력 항목입니다.")
        @Size(min = 8, message = "최소 8글자 이상 입력하세요.")
        private String uid;

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, message = "최소 8글자 이상 입력하세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "비밀번호는 대/소문자, 숫자, 특수문자를 포함해야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        private String name;

        private String nickname;

        @NotBlank(message = "생년월일은 필수 입력 항목입니다.")
        private String birthday;

        @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "유효하지 않은 휴대전화번호 형식입니다. (예: 01012345678)")
        private String mdn;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {

        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, message = "최소 8글자 이상 입력하세요.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "비밀번호는 대/소문자, 숫자, 특수문자를 포함해야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        private String name;

        @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "유효하지 않은 휴대전화번호 형식입니다. (예: 01012345678)")
        private String mdn;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {

        private String uid;

        private String name;

        private String nickname;

        private String birthday;

        private String mdn;

        public static Response of(Member member) {
            return Response.builder()
                    .uid(member.getUid())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .birthday(member.getBirthday())
                    .mdn(member.getMdn())
                .build();
        }

        public static Response getSensitiveData(Member member) {
            return Response.builder()
                    .uid(maskingSensitiveData(member.getUid(), Response::maskingUid))
                    .name(maskingSensitiveData(member.getName(), Response::maskingName))
                    .mdn(maskingSensitiveData(member.getMdn(), Response::maskingMdn))
                .build();
        }

        private static String maskingSensitiveData(String str, Function<String, String> maskingFunction) {
            return maskingFunction.apply(str);
        }

        private static String maskingMdn(String str) {
            return str.substring(0, 3) + "*" +
                    str.charAt(4) + "***" +
                    str.substring(8);
        }

        private static String maskingName(String str) {
            StringBuilder maskedName = new StringBuilder(str);
            if(str.length() > 1) {
                for(int i = 1; i < Math.max(2, str.length() - 1); i++) {
                    maskedName.setCharAt(i, '*');
                }
            }
            return maskedName.toString();
        }

        private static String maskingUid(String str) {
            String maskedChars = "*".repeat(8);
            return str.substring(0, str.length() - 8) + maskedChars;
        }
    }

}
