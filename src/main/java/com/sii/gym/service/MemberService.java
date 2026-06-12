package com.sii.gym.service;

import com.sii.gym.dto.MemberResponse;
import com.sii.gym.dto.RegisterMemberRequest;
import com.sii.gym.exception.MaximumCapacityExceededException;
import com.sii.gym.exception.ResourceNotFoundException;
import com.sii.gym.mapper.MemberMapper;
import com.sii.gym.model.Member;
import com.sii.gym.model.MembershipPlan;
import com.sii.gym.model.enums.MemberStatus;
import com.sii.gym.repository.MemberRepository;
import com.sii.gym.repository.MembershipPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MembershipPlanRepository membershipPlanRepository;

    private final MemberMapper memberMapper;

    public MemberResponse registerMember(RegisterMemberRequest request) {

        MembershipPlan membershipPlan = membershipPlanRepository.findById(request.membershipPlanId())
                .orElseThrow(() -> new ResourceNotFoundException(MembershipPlan.class, request.membershipPlanId()));

        long membersCount = memberRepository.countByMembershipPlanIdAndMemberStatus(request.membershipPlanId(), MemberStatus.ACTIVE);
        long maxMembers = membershipPlan.getMaxMembers();
        if(membersCount >= maxMembers) {
            throw new MaximumCapacityExceededException(request.membershipPlanId());
        }

        Member member = memberMapper.toEntity(request);
        member.setMembershipPlan(membershipPlan);
        member.setMemberStatus(MemberStatus.ACTIVE);
        member.setMembershipStartDate(LocalDate.now());

        Member savedMember = memberRepository.save(member);

        return memberMapper.toResponse(savedMember);
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toResponse)
                .toList();
    }

    public MemberResponse cancelMembership(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(Member.class, memberId));

        member.setMemberStatus(MemberStatus.CANCELLED);

        Member savedMember = memberRepository.save(member);

        return memberMapper.toResponse(savedMember);
    }
}