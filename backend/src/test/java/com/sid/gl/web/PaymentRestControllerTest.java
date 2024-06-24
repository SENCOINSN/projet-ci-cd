package com.sid.gl.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.entities.PaymentStatus;
import com.sid.gl.entities.PaymentType;
import com.sid.gl.entities.Student;
import com.sid.gl.exceptions.PaymentNotFoundException;
import com.sid.gl.services.PaymentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;


@ActiveProfiles("test")
@WebMvcTest(PaymentRestController.class)
public class PaymentRestControllerTest {
    @MockBean
    private PaymentService paymentService;
    @Autowired
    private MockMvc mockMvc;

    List<PaymentResponse> payments;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        Student student = Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build();
        Student student1 = Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build();
        Student student2 = Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build();
        this.payments =List.of(
                PaymentResponse.builder().file("file").id(1L).date(LocalDate.now()).status(PaymentStatus.VALIDATED).amount(1200).type(PaymentType.CASH).studentCode(student.getCode()).build(),
                PaymentResponse.builder().file("file1").id(2L).date(LocalDate.now()).status(PaymentStatus.CREATED).amount(1200).type(PaymentType.CASH).studentCode(student1.getCode()).build(),
                PaymentResponse.builder().file("file2").id(3L).date(LocalDate.now()).status(PaymentStatus.CREATED).amount(1200).type(PaymentType.CASH).studentCode(student2.getCode()).build()
        );
    }


    @Test
    public void shouldGetAllPayments() throws Exception {
        Mockito.when(paymentService.findAll()).thenReturn(payments);
        mockMvc.perform(MockMvcRequestBuilders.get("/payments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(payments)));
    }

    @Test
    public void shouldGetAllPaymentsByStatus() throws Exception {
        List<PaymentResponse> paymentsByStatus =  List.of(payments.get(1),payments.get(2));
        Mockito.when(paymentService.allPaymentByStatus(PaymentStatus.CREATED)).thenReturn(paymentsByStatus);
        mockMvc.perform(MockMvcRequestBuilders.get("/paymentsByStatus?paymentStatus="+PaymentStatus.CREATED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(paymentsByStatus)));
    }

    @Test
    public void shouldGetAllPaymentsByCode() throws Exception {
        String code ="code2";
        List<PaymentResponse> paymentsByStudentCode =  List.of(payments.get(2));
        Mockito.when(paymentService.allPaymentsByCode(code)).thenReturn(paymentsByStudentCode);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{code}/payments",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(paymentsByStudentCode)));
    }

    @Test
    void testUpdatePaymentStatus() throws Exception {
        Long id=1L;
        PaymentResponse paymentResponse= payments.get(0);
        Mockito.when(paymentService.updatePaymentStatus(Mockito.eq(PaymentStatus.VALIDATED),Mockito.eq(id))).thenReturn(payments.get(0));
        mockMvc.perform(MockMvcRequestBuilders.put("/payments?id="+id+"&paymentStatus="+PaymentStatus.VALIDATED)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(paymentResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(paymentResponse)));
    }

    @Test
    void shouldNotGetPaymentByInvalidId() throws Exception {
        Long id = 9L;
        Mockito.when(paymentService.getPaymentById(id)).thenThrow(PaymentNotFoundException.class);
        //doThrow(new PaymentNotFoundException("payment not found")).when(paymentService).getPaymentById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/payments/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void shouldGetPaymentById() throws Exception {
        Long id = 1L;
        Mockito.when(paymentService.getPaymentById(id)).thenReturn(payments.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get("/payments/{id}",id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(payments.get(0))));
    }

}