package com.ideate.ideaapiserver.service;

import com.ideate.ideaapiserver.config.constant.ErrorCode;
import com.ideate.ideaapiserver.dto.resource.ResourceDto;
import com.ideate.ideaapiserver.entity.Member;
import com.ideate.ideaapiserver.entity.Resource;
import com.ideate.ideaapiserver.handler.GlobalException;
import com.ideate.ideaapiserver.repository.ResourceRepository;
import com.ideate.ideaapiserver.util.GlobalUtils;
import com.ideate.ideaapiserver.util.ResourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        ResourceDto resourceDto = uploadImgFile(image);
        Resource saveResource = resourceRepository.save(Resource.create(resourceDto));
        return saveResource.getId();
    }

    @Transactional
    public Long update(Long id, MultipartFile image) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(()-> new GlobalException(ErrorCode.NOT_FOUND_RESOURCE));

        deleteImgFile(resource.getPath() + resource.getFakeName() + resource.getOriginalName());

        ResourceDto resourceDto = uploadImgFile(image);

        resource.update(resourceDto);


        return resource.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(()-> new GlobalException(ErrorCode.NOT_FOUND_RESOURCE));

        deleteImgFile(resource.getPath() + resource.getFakeName() + resource.getOriginalName());

        Optional.ofNullable(resource.getMember())
                .ifPresentOrElse(Member::deleteResource, ()-> resourceRepository.delete(resource));

        return resource.getId();
    }

    public ResourceDto uploadImgFile(MultipartFile file) {
        try {
            createDirectory(resourceProperties.getDir());

            ResourceDto resourceDto = saveFile(file, resourceProperties.getUploadPath());

            return resourceDto;
        } catch (IOException e) {
            throw new GlobalException(e, ErrorCode.FILE_UPLOAD_FAIL);
        }
    }

    public void deleteImgFile(String filePath) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            throw new GlobalException(e, ErrorCode.FILE_DOWNLOAD_FAIL);
        }
    }

    private void createDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private ResourceDto saveFile(MultipartFile file, String uploadPath) throws IOException {
        String uuid = GlobalUtils.makeUUID(16);
        String fullPath = uploadPath + uuid + file.getOriginalFilename();

        Path path = Paths.get(fullPath);
        Files.write(path, file.getBytes());

        return ResourceDto.builder()
                .fakeName(uuid)
                .originalName(file.getOriginalFilename())
                .path(uploadPath)
                .build();
    }

}
