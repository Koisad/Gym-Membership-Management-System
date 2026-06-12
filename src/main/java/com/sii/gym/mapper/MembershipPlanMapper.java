package com.sii.gym.mapper;

import com.sii.gym.dto.CreateMembershipPlanRequest;
import com.sii.gym.dto.MembershipPlanResponse;
import com.sii.gym.model.MembershipPlan;
import org.springframework.stereotype.Component;

@Component
public class MembershipPlanMapper {

    public MembershipPlan toEntity(CreateMembershipPlanRequest request) {
        MembershipPlan membershipPlan = new MembershipPlan();
        membershipPlan.setName(request.name());
        membershipPlan.setMembershipPlanType(request.membershipPlanType());
        membershipPlan.setMonthlyPrice(request.monthlyPrice());
        membershipPlan.setCurrency(request.currency());
        membershipPlan.setDurationMonths(request.durationMonths());
        membershipPlan.setMaxMembers(request.maxMembers());

        return membershipPlan;
    }

    public MembershipPlanResponse toResponse(MembershipPlan membershipPlan) {
        if(membershipPlan == null) return null;

        return new MembershipPlanResponse(
                membershipPlan.getId(),
                membershipPlan.getName(),
                membershipPlan.getMembershipPlanType(),
                membershipPlan.getMonthlyPrice(),
                membershipPlan.getCurrency(),
                membershipPlan.getDurationMonths(),
                membershipPlan.getMaxMembers(),
                membershipPlan.getGym().getId()
        );
    }
}
