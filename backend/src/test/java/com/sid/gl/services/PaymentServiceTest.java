package com.sid.gl.services;

import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.Payment;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import com.sid.gl.exceptions.PaymentNotFoundException;
import com.sid.gl.repository.PaymentRepository;
import com.sid.gl.repository.StudentRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void shouldGetAllPayments(){
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

        List<Payment> payments = List.of(
                Payment.builder().file("file").id(1L).date(LocalDate.now()).paymentStatus(PaymentStatus.VALIDATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student)
                        .build(),
                Payment.builder().file("file1").id(2L).date(LocalDate.now()).paymentStatus(PaymentStatus.CREATED)
                        .amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student1)
                        .build(),
                Payment.builder().file("file2").id(3L).date(LocalDate.now()).paymentStatus(PaymentStatus.REJECTED).amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student2)
                        .build()

        );

        List<PaymentResponse> expected = List.of(
                PaymentResponse.builder().file("file").id(1L).date(LocalDate.now()).status(PaymentStatus.VALIDATED)
                        .amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student.getCode())
                        .build(),
                PaymentResponse.builder().file("file1").id(2L).date(LocalDate.now()).status(PaymentStatus.CREATED)
                        .amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student1.getCode())
                        .build(),
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.REJECTED).amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student2.getCode())
                        .build()

        );
        Mockito.when(paymentRepository.findAll()).thenReturn(payments);
        List<PaymentResponse> result = paymentService.findAll();
        assertEquals(3,result.size());
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void shouldRetrieveAllPaymentsByCode(){
        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

        List<Payment> payments = List.of(
                Payment.builder().file("file2").id(3L).date(LocalDate.now()).paymentStatus(PaymentStatus.REJECTED).amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student2)
                        .build()

        );

        List<PaymentResponse> expected = List.of(
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.REJECTED).amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student2.getCode())
                        .build()
        );

        Mockito.when(paymentRepository.findByStudentCode("code2")).thenReturn(payments);
        List<PaymentResponse> result = paymentService.allPaymentsByCode("code2");
        assertEquals(1,result.size());
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void should_retrieve_payments_by_status(){
        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

        List<Payment> payments = List.of(
                Payment.builder().file("file2").id(3L).date(LocalDate.now()).paymentStatus(PaymentStatus.REJECTED).amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student2)
                        .build()

        );

        List<PaymentResponse> expected = List.of(
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.REJECTED).amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student2.getCode())
                        .build()
        );

        Mockito.when(paymentRepository.findByPaymentStatus(PaymentStatus.REJECTED)).thenReturn(payments);
        List<PaymentResponse> result = paymentService.allPaymentByStatus(PaymentStatus.REJECTED);
        assertEquals(1,result.size());
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


    @Test
    public void should_retrieve_payments_by_type(){
        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

        List<Payment> payments = List.of(
                Payment.builder().file("file2").id(3L).date(LocalDate.now()).paymentStatus(PaymentStatus.REJECTED).amount(1200)
                        .paymentType(PaymentType.CASH)
                        .student(student2)
                        .build()

        );

        List<PaymentResponse> expected = List.of(
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.REJECTED).amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student2.getCode())
                        .build()
        );

        Mockito.when(paymentRepository.findByPaymentType(PaymentType.CASH)).thenReturn(payments);
        List<PaymentResponse> result = paymentService.allPaymentsByType(PaymentType.CASH);
        assertEquals(1,result.size());
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }



    @Test
    public void shouldNotGetPaymentIdNotExist(){
        Long id=9L;
        Mockito.when(paymentRepository.findById(id)).thenReturn(Optional.empty());
        AssertionsForClassTypes.assertThatThrownBy(()->paymentService.getPaymentById(id))
                .isInstanceOf(PaymentNotFoundException.class);
    }

    @Test
    void shouldRetrievePayment() throws PaymentNotFoundException {
        Student student2 = Student.builder()
                .programId("programId")
                .lastName("lastname1")
                .firstName("firstname1")
                .code("code2")
                .id("id2")
                .photo("photo")
                .build();

        Long paymentId =3L;
        Payment payment =  Payment.builder().file("file2").id(3L).date(LocalDate.now()).paymentStatus(PaymentStatus.REJECTED).amount(1200)
                .paymentType(PaymentType.CASH)
                .student(student2)
                .build();
        PaymentResponse expected =
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.REJECTED).amount(1200)
                        .type(PaymentType.CASH)
                        .studentCode(student2.getCode())
                        .build();

        Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        PaymentResponse result = paymentService.getPaymentById(paymentId);
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void should_update_payment_status(){
        Student student = Student.builder()
                .programId("programId")
                .lastName("lastname")
                .firstName("firstname")
                .code("code")
                .id("id")
                .photo("photo")
                .build();

       Long paymentId=1L;
       Payment payment =  Payment.builder().file("file").id(1L).date(LocalDate.now())
               .paymentStatus(PaymentStatus.CREATED)
               .amount(1200)
               .paymentType(PaymentType.CASH)
               .student(student)
               .build();

        Payment paymentExpected =  Payment.builder().file("file").id(1L).date(LocalDate.now())
                .paymentStatus(PaymentStatus.VALIDATED)
                .amount(1200)
                .paymentType(PaymentType.CASH)
                .student(student)
                .build();
       PaymentResponse expected =  PaymentResponse.builder().file("file").id(1L).date(LocalDate.now())
               .status(PaymentStatus.VALIDATED)
               .amount(1200)
               .type(PaymentType.CASH)
               .studentCode(student.getCode())
               .build();

       Mockito.when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
       Mockito.when(paymentRepository.save(payment)).thenReturn(paymentExpected);
       PaymentResponse result = paymentService.updatePaymentStatus(PaymentStatus.VALIDATED,paymentId);
       assertNotNull(result);
       AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

}