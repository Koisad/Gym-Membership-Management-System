package com.sii.gym.dto;

import com.sii.gym.model.enums.MemberStatus;

import java.time.LocalDate;

public record MemberResponse(
        Long id,
        String fullName,
        String email,
        LocalDate membershipStartDate,
        MemberStatus memberStatus,
        String planName,
        String gymName
) {}