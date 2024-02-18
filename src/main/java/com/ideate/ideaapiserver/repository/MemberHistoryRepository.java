package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.config.constant.HistoryType;
import com.ideate.ideaapiserver.entity.MemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberHistoryRepository extends JpaRepository<MemberHistory, Long> {

    List<MemberHistory> findAllByUid(String uid);
}
