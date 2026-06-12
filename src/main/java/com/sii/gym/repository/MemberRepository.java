package com.sii.gym.repository;

import com.sii.gym.model.Member;
import com.sii.gym.model.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    long countByMembershipPlanIdAndMemberStatus(Long membershipPlanId, MemberStatus memberStatus);
}