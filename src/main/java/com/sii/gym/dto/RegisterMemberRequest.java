package com.sii.gym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterMemberRequest(
        @NotBlank(message = "Full name cannot be blank")
        String fullName,

        @NotBlank(message = "Email address cannot be blank")
        @Email(message = "Email must be a valid format")
        String email,

        @NotNull(message = "Membership plan ID cannot be null")
        Long membershipPlanId
) {}