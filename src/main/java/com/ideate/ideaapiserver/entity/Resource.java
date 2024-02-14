package com.ideate.ideaapiserver.entity;


import com.ideate.ideaapiserver.dto.member.ResourceDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESOURCES")
@Getter
@ToString(exclude = "member")
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    private String originalName;

    private String fakeName;

    @OneToOne(mappedBy = "resource", fetch = FetchType.LAZY)
    private Member member;

    public static Resource create(ResourceDto dto) {
        return Resource.builder()
                .path(dto.getPath())
                .originalName(dto.getOriginalName())
                .fakeName(dto.getFakeName())
            .build();
    }

    public void update(ResourceDto dto) {
        this.path = dto.getPath();
        this.originalName = dto.getOriginalName();
        this.fakeName = dto.getFakeName();
    }
}

