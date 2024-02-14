package com.ideate.ideaapiserver.entity;


import com.ideate.ideaapiserver.dto.member.ResourceDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RESOURCES")
@Getter
@ToString
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

    public static Resource create(ResourceDto dto) {
        return Resource.builder()
                .path(dto.getPath())
                .originalName(dto.getOriginalName())
                .fakeName(dto.getFakeName())
            .build();
    }
}

