package com.sii.gym.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateGymRequest(
        @NotBlank(message = "Gym name cannot be blank")
        String name,

        @NotBlank(message = "Address cannot be blank")
        String address,

        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber
) {}