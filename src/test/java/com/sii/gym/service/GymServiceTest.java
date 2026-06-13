package com.sii.gym.service;

import com.sii.gym.dto.CreateGymRequest;
import com.sii.gym.dto.GymResponse;
import com.sii.gym.mapper.GymMapper;
import com.sii.gym.model.Gym;
import com.sii.gym.repository.GymRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GymServiceTest {

    @Mock
    private GymRepository gymRepository;

    @Mock
    private GymMapper gymMapper;

    @InjectMocks
    private GymService gymService;

    @Test
    void shouldCreateNewGym() {
        // GIVEN
        Long id = 1L;
        String name = "Gym";
        String address = "Warsaw";
        String phoneNumber = "123456789";
        CreateGymRequest request = new CreateGymRequest(name, address, phoneNumber);

        Gym mappedGym = new Gym();
        Gym savedGym = new Gym();

        GymResponse expected = new GymResponse(id, name, address, phoneNumber);

        when(gymRepository.existsByName(name)).thenReturn(false);
        when(gymMapper.toEntity(request)).thenReturn(mappedGym);
        when(gymRepository.save(mappedGym)).thenReturn(savedGym);
        when(gymMapper.toResponse(savedGym)).thenReturn(expected);

        // WHEN
        GymResponse response = gymService.create(request);

        // THEN
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(name, response.name());
        assertEquals(address, response.address());
        assertEquals(phoneNumber, response.phoneNumber());

        verify(gymRepository).save(mappedGym);
    }

    @Test
    void shouldThrowExceptionWhenGymNameAlreadyExists() {
        // GIVEN
        String name = "Gym";
        String address = "Warsaw";
        String phoneNumber = "123456789";
        CreateGymRequest request = new CreateGymRequest(name, address, phoneNumber);

        when(gymRepository.existsByName(name)).thenReturn(true);

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> gymService.create(request));
    }
}