package com.ideate.ideaapiserver.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ideate.ideaapiserver.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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

        public static Response getUidAndNameAndMdn(Member member) {
            return Response.builder()
                    .uid(maskUserid(member.getUid()))
                    .name(maskName(member.getName()))
                    .mdn(maskMdn(member.getMdn()))
                .build();
        }

        public static String maskUserid(String uid) {
            String maskedChars = "*".repeat(8);
            return uid.substring(0, uid.length() - 8) + maskedChars;
        }

        public static String maskName(String name) {
            StringBuilder maskedName = new StringBuilder(name);
            if(name.length() > 1) {
                for(int i = 1; i < Math.max(2, name.length() - 1); i++) {
                    maskedName.setCharAt(i, '*');
                }
            }
            return maskedName.toString();
        }
    }

    public static String maskMdn(String mdn) {
        String firstPart = mdn.substring(0, 3) + "*";
        String secondPart = mdn.charAt(4) + "***";
        String thirdPart = mdn.substring(8);


        return firstPart + secondPart + thirdPart;
    }

}
