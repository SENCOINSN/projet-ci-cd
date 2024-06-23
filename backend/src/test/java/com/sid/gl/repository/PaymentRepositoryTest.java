package com.sid.gl.repository;

import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class PaymentRepositoryTest {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void setup(){
        System.out.println("-----Init data student----");
        Student student = studentRepository.save(Student.builder()
                .programId("programId")
                .lastName("lastname")
                .firstName("firstname")
                .code("code")
                .id("id")
                .photo("photo")
                .build());

        Student student1 = studentRepository.save(Student.builder()
                .programId("programId")
                .lastName("lastname2")
                .firstName("firstname2")
                .code("code1")
                .id("id1")
                .photo("photo")
                .build());

        Student student2 = studentRepository.save(Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build());

        paymentRepository.save(Payment.builder()
                        .file("file")
                        .id(1L)
                        .date(LocalDate.now())
                        .paymentStatus(PaymentStatus.VALIDATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student)
                .build());

        paymentRepository.save(Payment.builder()
                .file("file1")
                .id(2L)
                .date(LocalDate.now())
                .paymentStatus(PaymentStatus.CREATED)
                .amount(1200)
                .paymentType(PaymentType.CASH)
                .student(student1)
                .build());

        paymentRepository.save(Payment.builder()
                .file("file2")
                .id(3L)
                .date(LocalDate.now())
                .paymentStatus(PaymentStatus.REJECTED)
                .amount(1200)
                .paymentType(PaymentType.CASH)
                .student(student2)
                .build());
        System.out.println("------------------------------");

    }

    @Test
    public void shouldAlPayments(){
        Student student = Student.builder()
                .programId("programId")
                .lastName("lastname")
                .firstName("firstname")
                .code("code")
                .id("id")
                .photo("photo")
                .build();

        Student student1 = Student.builder()
                .programId("programId")
                .lastName("lastname2")
                .firstName("firstname2")
                .code("code1")
                .id("id1")
                .photo("photo")
                .build();

        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

        List<Payment> expected = List.of(
                Payment.builder()
                        .file("file")
                        .id(1L)
                        .date(LocalDate.now())
                        .paymentStatus(PaymentStatus.VALIDATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student)
                        .build(),
                Payment.builder()
                        .file("file1")
                        .id(2L)
                        .date(LocalDate.now())
                        .paymentStatus(PaymentStatus.CREATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student1)
                        .build(),
                Payment.builder()
                        .file("file2")
                        .id(3L)
                        .date(LocalDate.now())
                        .paymentStatus(PaymentStatus.REJECTED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student2)
                        .build()

        );

        List<Payment> result = paymentRepository.findAll();
        assertThat(result.size()).isEqualTo(3);
    }
    @Test
    public void should_find_paymentByCode(){
        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

      List<Payment> payments = List.of(
              Payment.builder()
                      .file("file2")
                      .id(3L)
                      .date(LocalDate.now())
                      .paymentStatus(PaymentStatus.REJECTED)
                      .amount(1200)
                      .paymentType(PaymentType.CASH)
                      .student(student2)
                      .build()

      );
      ArrayList<Payment> result  = (ArrayList<Payment>) paymentRepository.findByStudentCode("code2");
      assertThat(result.size()).isEqualTo(1);
       // assertThat(payments).isEqualTo(result);
        assertThat(payments).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);

    }

    @Test
    public void should_findPaymentsByStatus(){
        Student student1 = Student.builder()
                .programId("programId")
                .lastName("lastname2")
                .firstName("firstname2")
                .code("code1")
                .id("id1")
                .photo("photo")
                .build();

        List<Payment> payments = List.of(
                Payment.builder()
                        .file("file1")
                        .id(2L)
                        .date(LocalDate.now())
                        .paymentStatus(PaymentStatus.CREATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student1)
                        .build()

        );
        ArrayList<Payment> result  = (ArrayList<Payment>) paymentRepository.findByPaymentStatus(PaymentStatus.CREATED);
        assertThat(result.size()).isEqualTo(1);
        // assertThat(payments).isEqualTo(result);
        assertThat(payments).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);

    }


    @Test
    public void should_NotFindPaymentsByType(){
        List<Payment> expected = new ArrayList<>();
        ArrayList<Payment> result  = (ArrayList<Payment>) paymentRepository.findByPaymentType(PaymentType.CHECK);
        assertThat(result.size()).isEqualTo(0);
         assertThat(expected).isEqualTo(result);
       // assertThat(payments).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);

    }

}