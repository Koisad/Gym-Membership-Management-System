package com.sii.gym.controller;

import com.sii.gym.dto.CreateGymRequest;
import com.sii.gym.dto.GymResponse;
import com.sii.gym.dto.RevenueReportResponse;
import com.sii.gym.service.GymService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gyms")
public class GymController {

    private final GymService gymService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GymResponse createGym(@Valid @RequestBody CreateGymRequest request) {
        return gymService.create(request);
    }

    @GetMapping
    public List<GymResponse> getAllGyms() {
        return gymService.getAllGyms();
    }

    @GetMapping("/report")
    public List<RevenueReportResponse> getRevenueReport() {
        return gymService.getRevenueReport();
    }
}