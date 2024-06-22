package com.sid.gl.dto;

import com.sid.gl.entities.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequest(
     @Positive(message = "amount must be positif") double amount,
     @NotNull(message = "payment type not be null") PaymentType type,
      @NotNull(message = "student code not be null")
      @NotBlank(message = "student code not be blank")
      String studentCode
) {
}
