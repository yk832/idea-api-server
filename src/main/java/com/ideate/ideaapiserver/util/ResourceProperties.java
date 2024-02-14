package com.ideate.ideaapiserver.util;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.io.File;

@Getter
@ConfigurationProperties(prefix = "upload")
@RequiredArgsConstructor
public class ResourceProperties {

    private final String dir;


    public String getUploadPath() {
        return dir + File.separator;
    }
}
