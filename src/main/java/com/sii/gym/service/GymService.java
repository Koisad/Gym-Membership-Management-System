package com.sii.gym.service;

import com.sii.gym.dto.CreateGymRequest;
import com.sii.gym.dto.GymResponse;
import com.sii.gym.dto.RevenueReportResponse;
import com.sii.gym.mapper.GymMapper;
import com.sii.gym.model.Gym;
import com.sii.gym.repository.GymRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GymService {

    private final GymRepository gymRepository;
    private final GymMapper gymMapper;

    public GymResponse create(CreateGymRequest request) {
        if(gymRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Gym with that name already exists");
        }

        Gym savedGym = gymRepository.save(gymMapper.toEntity(request));
        return gymMapper.toResponse(savedGym);
    }

    public List<GymResponse> getAllGyms() {
        return gymRepository.findAll()
                .stream()
                .map(gymMapper::toResponse)
                .toList();
    }

    public List<RevenueReportResponse> getRevenueReport() {
        return gymRepository.getReports();
    }
}