package com.ideate.ideaapiserver.repository;

import com.ideate.ideaapiserver.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
