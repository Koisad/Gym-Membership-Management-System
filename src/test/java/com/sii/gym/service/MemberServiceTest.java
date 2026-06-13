package com.sii.gym.service;

import com.sii.gym.dto.MemberResponse;
import com.sii.gym.dto.RegisterMemberRequest;
import com.sii.gym.exception.MaximumCapacityExceededException;
import com.sii.gym.mapper.MemberMapper;
import com.sii.gym.model.Member;
import com.sii.gym.model.MembershipPlan;
import com.sii.gym.model.enums.MemberStatus;
import com.sii.gym.repository.MemberRepository;
import com.sii.gym.repository.MembershipPlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MembershipPlanRepository membershipPlanRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    @Test
    void shouldThrowExceptionWhenCapacityIsExceeded() {
        // GIVEN
        Long planId = 1L;
        int maxMembers = 5;
        RegisterMemberRequest request = new RegisterMemberRequest("Jan Kowal", "jan@example.com", planId);
        MembershipPlan membershipPlan = new MembershipPlan();
        membershipPlan.setId(planId);
        membershipPlan.setMaxMembers(maxMembers);

        when(membershipPlanRepository.findById(planId)).thenReturn(Optional.of(membershipPlan));
        when(memberRepository.countByMembershipPlanIdAndMemberStatus(planId, MemberStatus.ACTIVE)).thenReturn(5L);

        // WHEN & THEN
        assertThrows(MaximumCapacityExceededException.class, () -> memberService.registerMember(request));
    }

    @Test
    void shouldRegisterNewMember() {
        // GIVEN
        Long planId = 1L;
        int maxMembers = 5;
        String name = "Jan Kowal";
        String email = "jan@example.com";

        RegisterMemberRequest request = new RegisterMemberRequest(name, email, planId);

        MembershipPlan membershipPlan = new MembershipPlan();
        membershipPlan.setId(planId);
        membershipPlan.setMaxMembers(maxMembers);

        Member member = new Member();
        MemberResponse expected = new MemberResponse(1L, name, email, LocalDate.now(), MemberStatus.ACTIVE, "Plan", "Gym");

        when(membershipPlanRepository.findById(planId)).thenReturn(Optional.of(membershipPlan));
        when(memberRepository.countByMembershipPlanIdAndMemberStatus(planId, MemberStatus.ACTIVE)).thenReturn(1L);
        when(memberMapper.toEntity(request)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);
        when(memberMapper.toResponse(member)).thenReturn(expected);

        // WHEN
        MemberResponse response = memberService.registerMember(request);

        // THEN
        assertNotNull(response);
        assertEquals(name, response.fullName());
        assertEquals(email, response.email());

        verify(memberRepository).save(member);
    }
}