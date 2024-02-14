package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.ErrorCode;
import com.ideate.ideaapiserver.dto.member.ResourceDto;
import com.ideate.ideaapiserver.entity.Resource;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.ResourceRepository;
import com.ideate.ideaapiserver.util.GlobalUtils;
import com.ideate.ideaapiserver.util.ResourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceProperties resourceProperties;

    public ResourceDto uploadResource(MultipartFile file) {
        try {

            File directory = new File(resourceProperties.getDir());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String uuid = GlobalUtils.makeUUID(16);
            String absolutePath = resourceProperties.getUploadPath();
            String fullPath = absolutePath + uuid + file.getOriginalFilename();

            Path path = Paths.get(fullPath);
            Files.write(path, file.getBytes());

            return ResourceDto.builder()
                    .fakeName(uuid)
                    .originalName(file.getOriginalFilename())
                    .path(absolutePath)
                .build();

        } catch (IOException e) {
            throw new GlobalException(e, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
