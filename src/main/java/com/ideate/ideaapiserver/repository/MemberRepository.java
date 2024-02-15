package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"resource"})
    Optional<Member> findById(Long id);

    Optional<Member> findByUid(String userid);

    List<Member> findByUidIn(List<String> useridList);

    void deleteByUidIn(List<String> deleteMembers);
}
