package com.sii.gym.dto;

import com.sii.gym.model.enums.Currency;
import com.sii.gym.model.enums.MembershipPlanType;

import java.math.BigDecimal;

public record MembershipPlanResponse(
        Long id,
        String name,
        MembershipPlanType membershipPlanType,
        BigDecimal monthlyPrice,
        Currency currency,
        Integer durationMonths,
        Integer maxMembers,
        Long gymId
) {}