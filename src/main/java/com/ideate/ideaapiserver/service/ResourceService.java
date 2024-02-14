package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.ErrorCode;
import com.ideate.ideaapiserver.dto.member.ResourceDto;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.Resource;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.ResourceRepository;
import com.ideate.ideaapiserver.util.GlobalUtils;
import com.ideate.ideaapiserver.util.ResourceProperties;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceProperties resourceProperties;

    private final ResourceRepository resourceRepository;

    public List<ResourceDto> findAll() {
        return resourceRepository.findAll().stream()
                .map(ResourceDto::of)
                .collect(Collectors.toList());
    }

    public ResourceDto findById(Long id) {
        return resourceRepository.findById(id)
                .map(ResourceDto::of)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_RESOURCE));
    }

    @Transactional
    public Long save(MultipartFile image) {
        ResourceDto resourceDto = uploadResource(image);
        Resource saveResource = resourceRepository.save(Resource.create(resourceDto));
        return saveResource.getId();
    }

    @Transactional
    public Long update(Long id, MultipartFile image) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(()-> new GlobalException(ErrorCode.NOT_FOUND_RESOURCE));

        deleteFile(resource.getPath() + resource.getFakeName() + resource.getOriginalName());

        ResourceDto resourceDto = uploadResource(image);

        resource.update(resourceDto);


        return resource.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(()-> new GlobalException(ErrorCode.NOT_FOUND_RESOURCE));

        deleteFile(resource.getPath() + resource.getFakeName() + resource.getOriginalName());

        Optional.ofNullable(resource.getMember())
                .ifPresentOrElse(Member::deleteResource, ()-> resourceRepository.delete(resource));

        return resource.getId();
    }

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

    public void deleteFile(String filePath) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new GlobalException(e, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
