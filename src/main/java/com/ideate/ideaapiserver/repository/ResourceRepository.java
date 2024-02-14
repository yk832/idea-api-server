package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
