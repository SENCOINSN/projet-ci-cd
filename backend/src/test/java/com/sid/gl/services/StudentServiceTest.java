package com.sid.gl.services;

import com.sid.gl.dto.StudentResponse;
import com.sid.gl.entities.Student;
import com.sid.gl.repository.StudentRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    public void shouldGetAllStudents(){
        List<Student> students = List.of(
                Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );

        List<StudentResponse> expected = List.of(
                StudentResponse.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );

        Mockito.when(studentRepository.findAll()).thenReturn(students);
        //Mockito.when(StudentMapper.toListStudentResponse(students))
                //.thenReturn(expected);
        List<StudentResponse> result= studentService.allStudents();
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void should_studentByCode(){
        Student student = Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build();
        StudentResponse expected =  StudentResponse.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build();
        Mockito.when(studentRepository.findByCode("code2")).thenReturn(student);
        StudentResponse result = studentService.getStudentByCode("code2");
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    public void should_studentByCodeNotExist(){
        String code ="code4";

        Mockito.when(studentRepository.findByCode("code4")).thenReturn(null);
        StudentResponse result = studentService.getStudentByCode("code4");
        assertNull(result);
        //AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }


    @Test
    public void should_list_student_by_programId(){
        List<Student> students = List.of(
                Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );

        List<StudentResponse> expected = List.of(
                StudentResponse.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );

        Mockito.when(studentRepository.findByProgramId("programId")).thenReturn(students);
        //Mockito.when(StudentMapper.toListStudentResponse(students))
        //.thenReturn(expected);
        List<StudentResponse> result= studentService.getStudentByProgramId("programId");
        assertNotNull(result);
        AssertionsForClassTypes.assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    public void shouldListStudentGroupByProgramId(){
        List<Student> students = List.of(
                Student.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                Student.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                Student.builder().programId("programId1").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );

        List<StudentResponse> expected = List.of(
                StudentResponse.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build()
        );
        List<StudentResponse> expected1 = List.of(
                StudentResponse.builder().programId("programId1").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );
        Map<String,List<StudentResponse>> expectedMap = new HashMap<>();
        expectedMap.put("programId",expected);
        expectedMap.put("programId1",expected1);
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        Map<String,List<StudentResponse>> results = studentService.studentByProgramId();

        assertEquals(2,results.size());
        AssertionsForClassTypes.assertThat(expectedMap).usingRecursiveComparison().isEqualTo(results);
    }

}