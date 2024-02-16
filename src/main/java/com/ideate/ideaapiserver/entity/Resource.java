package com.ideate.ideaapiserver.entity;


import com.ideate.ideaapiserver.dto.resource.ResourceDto;
import com.ideate.ideaapiserver.dto.resource.ResourceInfo;
import jakarta.persistence.*;
import lombok.*;

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

    @Embedded
    ResourceInfo resourceInfo;

    @OneToOne(mappedBy = "resource", fetch = FetchType.LAZY)
    private Member member;

    public static Resource create(ResourceInfo resourceInfo) {
        return Resource.builder()
                .resourceInfo(resourceInfo)
            .build();
    }

    public void update(ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
    }
}

