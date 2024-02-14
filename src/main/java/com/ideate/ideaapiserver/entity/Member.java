package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.config.MemberEntityListener;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.util.SHA256;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "MEMBERS")
@Getter
@ToString
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@EntityListeners(value = MemberEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String nickname;

    private String birthday;

    private String mdn;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Resource resource;

    public static Member create(MemberDto.Create request, Resource resource) {
        return Member.builder()
                .uid(request.getUid())
                .password(new SHA256().encrypt(request.getPassword()))
                .name(request.getName())
                .nickname(setNickname(request))
                .birthday(request.getBirthday())
                .mdn(request.getMdn())
                .resource(resource)
            .build();
    }

    public static Member create(MemberDto.Create request) {
        return Member.builder()
                .uid(request.getUid())
                .password(new SHA256().encrypt(request.getPassword()))
                .name(request.getName())
                .nickname(setNickname(request))
                .birthday(request.getBirthday())
                .mdn(request.getMdn())
                .build();
    }

    private static String setNickname(MemberDto.Create request) {
        return StringUtils.hasText(request.getNickname()) ? request.getNickname() : request.getName();
    }

    public void update(MemberDto.Update request, Resource resource) {
        this.mdn = request.getMdn();
        this.password = new SHA256().encrypt(request.getPassword());
        this.name = request.getName();
        this.resource = resource;
    }

    public void update(MemberDto.Update request) {
        this.mdn = request.getMdn();
        this.password = new SHA256().encrypt(request.getPassword());
        this.name = request.getName();
    }
}
