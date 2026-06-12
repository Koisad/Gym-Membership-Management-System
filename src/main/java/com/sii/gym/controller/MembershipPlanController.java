package com.sii.gym.controller;

import com.sii.gym.dto.CreateMembershipPlanRequest;
import com.sii.gym.dto.MembershipPlanResponse;
import com.sii.gym.service.MembershipPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plans/{gymId}")
public class MembershipPlanController {

    private final MembershipPlanService  membershipPlanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipPlanResponse createPlan(
            @PathVariable Long gymId,
            @Valid @RequestBody CreateMembershipPlanRequest request) {
        return membershipPlanService.createForGym(gymId, request);
    }

    @GetMapping
    public List<MembershipPlanResponse> getPlans(@PathVariable Long gymId) {
        return membershipPlanService.getPlansForGym(gymId);
    }
}
