package com.sii.gym.service;

import com.sii.gym.dto.CreateMembershipPlanRequest;
import com.sii.gym.dto.MembershipPlanResponse;
import com.sii.gym.exception.ResourceNotFoundException;
import com.sii.gym.mapper.MembershipPlanMapper;
import com.sii.gym.model.Gym;
import com.sii.gym.model.MembershipPlan;
import com.sii.gym.repository.GymRepository;
import com.sii.gym.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;
    private final GymRepository gymRepository;

    private final MembershipPlanMapper membershipPlanMapper;

    public MembershipPlanResponse createForGym(Long gymId, CreateMembershipPlanRequest request) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new ResourceNotFoundException(Gym.class, gymId));

        MembershipPlan membershipPlan = membershipPlanMapper.toEntity(request);
        membershipPlan.setGym(gym);

        MembershipPlan savedPlan = membershipPlanRepository.save(membershipPlan);

        return membershipPlanMapper.toResponse(savedPlan);
    }

    public List<MembershipPlanResponse> getPlansForGym(Long gymId) {
        if(!gymRepository.existsById(gymId)) {
                throw new ResourceNotFoundException(Gym.class, gymId);
        }

        return membershipPlanRepository.findAllByGymId(gymId)
                .stream()
                .map(membershipPlanMapper::toResponse)
                .toList();
    }
}