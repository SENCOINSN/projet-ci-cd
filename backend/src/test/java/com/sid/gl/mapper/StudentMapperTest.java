package com.sid.gl.mapper;

import com.sid.gl.dto.StudentResponse;
import com.sid.gl.entities.Student;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    @Test
    public void should_map_to_StudentResponse(){
        Student student = new Student();
        student.setCode("code");
        student.setId("id");
        student.setPhoto("photo");
        student.setFirstName("firstname");
        student.setLastName("lastname");
        student.setProgramId("programId");

        StudentResponse result = StudentMapper.toStudentResponse(student);
        assertNotNull(result);
        assertEquals("code",result.code());
        assertEquals("firstname",result.firstName());
    }

    @Test
    public void should_map_to_listStudentResponse(){
        Student student = new Student();
        student.setCode("code");
        student.setId("id");
        student.setPhoto("photo");
        student.setFirstName("firstname");
        student.setLastName("lastname");
        student.setProgramId("programId");
        List<Student> lists = Collections.singletonList(student);

        List<StudentResponse> results = StudentMapper.toListStudentResponse(lists);
        assertEquals(1,results.size());
        assertEquals("firstname",results.get(0).firstName());

    }
}