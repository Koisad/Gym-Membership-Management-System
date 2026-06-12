package com.sii.gym.dto;

import com.sii.gym.model.enums.Currency;
import com.sii.gym.model.enums.MembershipPlanType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateMembershipPlanRequest(
        @NotBlank(message = "Membership name cannot be blank")
        String name,

        @NotNull(message = "Membership type cannot be null")
        MembershipPlanType membershipPlanType,

        @NotNull(message = "Monthly price cannot be null")
        @Positive(message = "Monthly price must be positive number")
        BigDecimal monthlyPrice,

        @NotNull(message = "Currency cannot be null")
        Currency currency,

        @NotNull(message = "Duration cannot be null")
        @Min(value = 1, message = "Duration must be at least 1 month")
        Integer durationMonths,

        @NotNull(message = "Max members cannot be null")
        @Min(value = 1, message = "Max members must be at least 1")
        Integer maxMembers
) {}