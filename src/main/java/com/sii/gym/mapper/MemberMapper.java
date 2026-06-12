package com.sii.gym.mapper;

import com.sii.gym.dto.MemberResponse;
import com.sii.gym.dto.RegisterMemberRequest;
import com.sii.gym.model.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(RegisterMemberRequest request) {
        Member member = new Member();
        member.setFullName(request.fullName());
        member.setEmail(request.email());

        return member;
    }

    public MemberResponse toResponse(Member member) {
        if(member == null) return null;

        return new MemberResponse(
                member.getId(),
                member.getFullName(),
                member.getEmail(),
                member.getMembershipStartDate(),
                member.getMemberStatus(),
                member.getMembershipPlan().getName(),
                member.getMembershipPlan().getGym().getName()
        );
    }
}