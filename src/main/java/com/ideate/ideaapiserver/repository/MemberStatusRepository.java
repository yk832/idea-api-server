package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.config.constant.MemberStatus;
import com.ideate.ideaapiserver.entity.LoginHistory;
import com.ideate.ideaapiserver.entity.MemberStatusHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberStatusRepository extends MongoRepository<MemberStatusHistory, String> {
    Optional<MemberStatusHistory> findByUid(String uid);

    List<MemberStatusHistory> findMemberStatusHistoriesByLastLoginDateLessThanEqual(LocalDateTime day);

    List<MemberStatusHistory> findMemberStatusHistoriesByMemberStatus(MemberStatus status);

}
