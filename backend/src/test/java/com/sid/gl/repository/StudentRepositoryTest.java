package com.sid.gl.repository;


import com.sid.gl.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTest {

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

        System.out.println("------------------------------");

    }

    @Test
    public void should_findAll_students(){
        List<Student> expected = List.of(
                Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );
        List<Student> result = studentRepository.findAll();
        assertThat(result.size()).isEqualTo(3);
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);

    }

    @Test
    public void shouldRetrieveStudentByCode(){
      String code ="code2";
      Student expected = Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build();
      Student result = studentRepository.findByCode(code);
      assertNotNull(result);
      assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);
    }

    @Test
    public void should_findAll_students_by_programId(){
        List<Student> expected = List.of(
                Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );
        List<Student> result = studentRepository.findByProgramId("programId");
        assertThat(result.size()).isEqualTo(3);
        assertThat(expected).usingRecursiveComparison().ignoringFields("id").isEqualTo(result);

    }

    @Test
    public void should_findAll_students_by_programId_empty(){
        List<Student> expected = new ArrayList<>();
        List<Student> result = studentRepository.findByProgramId("programId1");
        assertThat(result.size()).isEqualTo(0);
        assertThat(expected).isEqualTo(result);
    }



}