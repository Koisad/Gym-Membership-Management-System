package com.sii.gym.service;

import com.sii.gym.dto.CreateMembershipPlanRequest;
import com.sii.gym.dto.MembershipPlanResponse;
import com.sii.gym.exception.ResourceNotFoundException;
import com.sii.gym.mapper.MembershipPlanMapper;
import com.sii.gym.model.Gym;
import com.sii.gym.model.MembershipPlan;
import com.sii.gym.model.enums.Currency;
import com.sii.gym.model.enums.MembershipPlanType;
import com.sii.gym.repository.GymRepository;
import com.sii.gym.repository.MembershipPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MembershipPlanServiceTest {

    @Mock
    private MembershipPlanRepository membershipPlanRepository;

    @Mock
    private GymRepository gymRepository;

    @Mock
    private MembershipPlanMapper membershipPlanMapper;

    @InjectMocks
    private MembershipPlanService membershipPlanService;

    @Test
    void shouldThrowExceptionWhenGymNotFoundOnCreate() {
        // GIVEN
        Long gymId = 1L;
        CreateMembershipPlanRequest request = mock(CreateMembershipPlanRequest.class);

        when(gymRepository.findById(gymId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> membershipPlanService.createForGym(gymId, request));
    }

    @Test
    void shouldThrowExceptionWhenGymNotFoundOnPlans() {
        // GIVEN
        Long gymId = 1L;
        when(gymRepository.existsById(gymId)).thenReturn(false);

        // WHEN & THEN
        assertThrows(ResourceNotFoundException.class, () -> membershipPlanService.getPlansForGym(gymId));
    }

    @Test
    void shouldCreateNewMembershipPlan() {
        // GIVEN
        Long gymId = 1L;
        String name = "Plan";
        MembershipPlanType type = MembershipPlanType.GROUP;
        BigDecimal monthlyPrice = new BigDecimal("0.99");
        Currency currency = Currency.USD;
        Integer durationMonths = 10;
        Integer maxMembers = 10;


        CreateMembershipPlanRequest request = new CreateMembershipPlanRequest(name, type, monthlyPrice, currency, durationMonths, maxMembers);
        MembershipPlanResponse expected = new MembershipPlanResponse(1L, name, type, monthlyPrice, currency, durationMonths, maxMembers, gymId);
        Gym gym = new Gym();
        MembershipPlan membershipPlan = new MembershipPlan();
        MembershipPlan savedPlan = new MembershipPlan();

        when(gymRepository.findById(gymId)).thenReturn(Optional.of(gym));
        when(membershipPlanMapper.toEntity(request)).thenReturn(membershipPlan);
        when(membershipPlanRepository.save(membershipPlan)).thenReturn(savedPlan);
        when(membershipPlanMapper.toResponse(savedPlan)).thenReturn(expected);

        // WHEN
        MembershipPlanResponse response = membershipPlanService.createForGym(gymId, request);

        // THEN
        assertNotNull(response);
        assertEquals(name, response.name());
        assertEquals(type, response.membershipPlanType());
        assertEquals(monthlyPrice, response.monthlyPrice());
        assertEquals(currency, response.currency());
        assertEquals(durationMonths, response.durationMonths());
        assertEquals(maxMembers, response.maxMembers());

        verify(membershipPlanRepository).save(membershipPlan);
    }
}
