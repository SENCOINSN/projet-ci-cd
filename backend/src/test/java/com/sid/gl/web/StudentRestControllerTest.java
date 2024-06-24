package com.sid.gl.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.dto.StudentResponse;
import com.sid.gl.services.StudentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTest {
    @MockBean
   private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    List<StudentResponse> studentResponses;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        this.studentResponses = List.of(
                StudentResponse.builder().programId("programId").lastName("lastname").firstName("firstname").code("code").id("id").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname2").firstName("firstname2").code("code1").id("id1").photo("photo").build(),
                StudentResponse.builder().programId("programId").lastName("lastname1").firstName("firstname1").code("code2").id("id2").photo("photo").build()
        );
    }

    @Test
    public void shouldGetAllStudents() throws Exception {
        Mockito.when(studentService.allStudents()).thenReturn(studentResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentResponses)));
    }

    @Test
    void shouldGetStudentByCode() throws Exception {
        String code = "code2";
        Mockito.when(studentService.getStudentByCode(code)).thenReturn(studentResponses.get(2));
        mockMvc.perform(MockMvcRequestBuilders.get("/students/{code}",code))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentResponses.get(2))));
    }


    @Test
    public void shouldGetAllStudentsByProgramId() throws Exception {
        String programId="programId";
        Mockito.when(studentService.getStudentByProgramId(programId)).thenReturn(studentResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/studentsByProgram?programId="+programId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(studentResponses)));
    }

}