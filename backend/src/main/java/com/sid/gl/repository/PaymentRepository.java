package com.sid.gl.repository;

import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
  List<Payment> findByStudentCode(String code);
  List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

  List<Payment> findByPaymentType(PaymentType paymentType);
}
