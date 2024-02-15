package com.ideate.ideaapiserver.controller;


import com.ideate.ideaapiserver.dto.common.CommonResponse;
import com.ideate.ideaapiserver.dto.member.MemberDto;
import com.ideate.ideaapiserver.dto.member.ResourceDto;
import com.ideate.ideaapiserver.service.MemberService;
import com.ideate.ideaapiserver.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <pre>
 * ${package_name}
 * ${ResourceController}
 * </pre>
 *
 * @author : ${user}
 * @date : ${date} ${time}
 * @desc :
 * @version : 0.1
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/api/resources")
    public ResponseEntity<List<ResourceDto>> findAll() {
        return ResponseEntity.ok(resourceService.findAll());
    }

    @GetMapping("/api/resources/{id}")
    public ResponseEntity<ResourceDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.findById(id));
    }

    @PostMapping("/api/resources")
    public ResponseEntity<CommonResponse> createResource(@RequestPart MultipartFile image) {
        Long id = resourceService.save(image);
        return ResponseEntity.ok(new CommonResponse(id));
    }

    @PutMapping("/api/resources/{id}")
    public ResponseEntity<CommonResponse> updateResource(@PathVariable Long id, @RequestPart MultipartFile image) {
        resourceService.update(id, image);
        return ResponseEntity.ok(new CommonResponse(id));
    }

    @DeleteMapping("/api/resources/{id}")
    public ResponseEntity<CommonResponse> deleteResource(@PathVariable Long id) {
        resourceService.delete(id);
        return ResponseEntity.ok(new CommonResponse(id));
    }


}
