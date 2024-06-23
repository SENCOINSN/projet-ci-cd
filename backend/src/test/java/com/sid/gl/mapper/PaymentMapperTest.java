package com.sid.gl.mapper;

import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {

    @Test
    public void should_map_paymentResponse(){
        Student student = new Student();
        student.setCode("code");
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setPaymentType(PaymentType.CHECK);
        payment.setAmount(1200);
        payment.setFile("file");
        payment.setDate(LocalDate.now());

        payment.setStudent(student);

        PaymentResponse result = PaymentMapper.toPaymentResponse(payment);
        assertNotNull(result);
        assertEquals(1200,result.amount());
    }


    @Test
    public void shoul_map_list_paymentResponse(){
        Student student = new Student();
        student.setCode("code");
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setPaymentStatus(PaymentStatus.CREATED);
        payment.setPaymentType(PaymentType.CHECK);
        payment.setAmount(1200);
        payment.setFile("file");
        payment.setDate(LocalDate.now());

        payment.setStudent(student);

        List<Payment> lists = Collections.singletonList(payment);
        List<PaymentResponse> result = PaymentMapper.toListPaymentResponse(lists);
        assertEquals(1,result.size());
     }


}