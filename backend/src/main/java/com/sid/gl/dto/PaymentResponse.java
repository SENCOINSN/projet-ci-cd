package com.sid.gl.dto;

import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PaymentResponse(
        Long id,
        LocalDate date,
        double amount,
        PaymentType type,
        PaymentStatus status,
        String file,
        String studentCode
) {
}
