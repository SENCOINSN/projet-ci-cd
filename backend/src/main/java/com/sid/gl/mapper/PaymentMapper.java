package com.sid.gl.mapper;

import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PaymentMapper {

    public static PaymentResponse toPaymentResponse(Payment payment){
        if(payment !=null){
            return new PaymentResponse(
                    payment.getId(),
                    payment.getDate(),
                    payment.getAmount(),
                    payment.getPaymentType(),
                    payment.getPaymentStatus(),
                    payment.getFile(),
                    payment.getStudent().getCode()
            );
        }
        return null;
    }

    public static List<PaymentResponse> toListPaymentResponse(List<Payment> payments){
      return  payments
                .stream()
                .filter(Objects::nonNull)
                .map(PaymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
    }
}
