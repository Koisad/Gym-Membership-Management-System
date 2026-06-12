package com.sii.gym.mapper;

import com.sii.gym.dto.CreateGymRequest;
import com.sii.gym.dto.GymResponse;
import com.sii.gym.model.Gym;
import org.springframework.stereotype.Component;

@Component
public class GymMapper {

    public Gym toEntity(CreateGymRequest request) {
        Gym gym = new Gym();
        gym.setName(request.name());
        gym.setAddress(request.address());
        gym.setPhoneNumber(request.phoneNumber());

        return gym;
    }

    public GymResponse toResponse(Gym gym) {
        if(gym == null) return null;

        return new GymResponse(
                gym.getId(),
                gym.getName(),
                gym.getAddress(),
                gym.getPhoneNumber()
        );
    }
}