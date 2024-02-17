package com.ideate.ideaapiserver.entity;

import com.ideate.ideaapiserver.dto.resource.ResourceInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "login_histories")
public class LoginHistory {

    @Id
    private String id;

    private String uid;

    private String name;

    private ResourceInfo resourceInfo;

    public static LoginHistory create(Member member) {
        return LoginHistory.builder()
                .uid(member.getUid())
                .name(member.getName())
                .resourceInfo(member.getResource().resourceInfo)
            .build();
    }
}
