package com.sii.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaximumCapacityExceededException extends RuntimeException {
    public MaximumCapacityExceededException(Long membershipPlanId) {
        super("Maximum capacity for plan " + membershipPlanId + " exceeded");
    }
}
