package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {
}
