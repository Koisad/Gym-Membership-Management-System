package com.sii.gym.dto;

public record GymResponse(
        Long id,
        String name,
        String address,
        String phoneNumber
) {}