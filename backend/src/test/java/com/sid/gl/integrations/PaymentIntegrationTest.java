package com.sid.gl.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.dto.PaymentResponse;
import com.sid.gl.dto.StudentResponse;
import com.sid.gl.entities.PaymentStatus;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PaymentIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16");

    @Test
    void shouldGetAllPayments(){
        ResponseEntity<PaymentResponse[]> response = testRestTemplate.exchange("/payments", HttpMethod.GET, null, PaymentResponse[].class);
        List<PaymentResponse> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //System.out.println(content.size());
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(30);
        //AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(customers);
    }

    @Test
    public void shouldGetAllPaymentByStudentCode(){
        String code="code2";
        ResponseEntity<PaymentResponse[]> response = testRestTemplate.exchange("/students/"+code+"/payments", HttpMethod.GET, null, PaymentResponse[].class);
        List<PaymentResponse> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //System.out.println(content.size());
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(10);
    }

    @Test
    public void shouldGetAllPaymentByStatus(){
        ResponseEntity<PaymentResponse[]> response = testRestTemplate.exchange("/paymentsByStatus?paymentStatus="+ PaymentStatus.CREATED, HttpMethod.GET, null, PaymentResponse[].class);
        List<PaymentResponse> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //System.out.println(content.size());
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(30);
    }

    @Test
    public void shouldGetPaymentById(){
        Long id =1L;
        ResponseEntity<PaymentResponse> response =
                testRestTemplate.exchange("/payments/"+id, HttpMethod.GET,null,PaymentResponse.class);
        PaymentResponse content = response.getBody();
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).isNotNull();
        //AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(studentResponses.get(2));
    }

    /*@Test
    public void shouldNotGetPaymentById(){
        Long paymentId = 60L;
        ResponseEntity<PaymentResponse> response = testRestTemplate.exchange("/payments/"+paymentId, HttpMethod.GET, null, PaymentResponse.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }*/

    @Test
    @Rollback
    void shouldUpdateValidCustomer(){
        Long id = 1L;
        ResponseEntity<PaymentResponse> response = testRestTemplate.exchange("/payments?paymentStatus="+PaymentStatus.VALIDATED+"&id="+id, HttpMethod.PUT, null, PaymentResponse.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).isNotNull();
        assertEquals(PaymentStatus.VALIDATED,response.getBody().status());
        //AssertionsForClassTypes.assertThat(response.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(customerDTO);
    }
}
