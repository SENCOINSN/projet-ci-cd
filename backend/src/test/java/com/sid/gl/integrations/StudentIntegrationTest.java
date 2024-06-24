package com.sid.gl.integrations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.dto.StudentResponse;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class StudentIntegrationTest {
    @Autowired
  private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    List<StudentResponse> studentResponses;
    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16");
    @BeforeEach
    public void setUp(){
        this.studentResponses = List.of(
                StudentResponse.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );
    }

    @Test
    public void shouldGetAllStudents(){
        ResponseEntity<StudentResponse[]> response =
                testRestTemplate.exchange("/students", HttpMethod.GET,null,StudentResponse[].class);
        List<StudentResponse> content = Arrays.asList(response.getBody());
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(content.size()).isEqualTo(3);
       AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(studentResponses);
    }

    @Test
    public void shouldStudentByCode(){
        String code="code2";
        ResponseEntity<StudentResponse> response =
                testRestTemplate.exchange("/students/"+code, HttpMethod.GET,null,StudentResponse.class);
        StudentResponse content = response.getBody();
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        AssertionsForClassTypes.assertThat(response.getBody()).isNotNull();
        AssertionsForClassTypes.assertThat(content).usingRecursiveComparison().isEqualTo(studentResponses.get(2));
    }

    @Test
    void shouldNotFindStudentByCode(){
        String code = "code4";
        ResponseEntity<StudentResponse> response = testRestTemplate.exchange("/students/"+code, HttpMethod.GET, null, StudentResponse.class);
        AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNull(response.getBody());
    }
}
