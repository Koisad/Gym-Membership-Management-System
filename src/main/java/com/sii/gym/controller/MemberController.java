package com.sii.gym.controller;


import com.sii.gym.dto.MemberResponse;
import com.sii.gym.dto.RegisterMemberRequest;
import com.sii.gym.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse registerMember(@Valid @RequestBody RegisterMemberRequest request) {
        return memberService.registerMember(request);
    }

    @GetMapping
    public List<MemberResponse> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping("/{memberId}/cancel")
    public MemberResponse cancelMembership(@PathVariable Long memberId) {
        return memberService.cancelMembership(memberId);
    }
}