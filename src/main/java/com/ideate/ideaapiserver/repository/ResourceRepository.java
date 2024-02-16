package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.Resource;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Override
    @EntityGraph(attributePaths = {"member"})
    Optional<Resource> findById(Long id);
    
    @Override
    @EntityGraph(attributePaths = {"member"})
    List<Resource> findAll();

}
