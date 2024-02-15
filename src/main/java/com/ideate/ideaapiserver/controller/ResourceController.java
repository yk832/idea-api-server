package com.ideate.ideaapiserver.controller;


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
    public ResponseEntity<Long> createResource(@RequestPart MultipartFile image) {
        return ResponseEntity.ok(resourceService.save(image));
    }

    @PutMapping("/api/resources/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestPart MultipartFile image) {
        return ResponseEntity.ok(resourceService.update(id, image));
    }

    @DeleteMapping("/api/resources/{id}")
    public ResponseEntity<Long> deleteResource(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.delete(id));
    }


}
