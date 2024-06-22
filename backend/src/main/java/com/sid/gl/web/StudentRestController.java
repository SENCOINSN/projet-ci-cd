package com.sid.gl.web;

import com.sid.gl.dto.StudentResponse;
import com.sid.gl.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StudentRestController {
    private final StudentService studentService;

    @GetMapping(path = "/students")
    public List<StudentResponse> allStudents(){
        return studentService.allStudents();
    }

    @GetMapping(path = "/students/{code}")
    public StudentResponse getStudentByCode(@PathVariable("code") String code){
        return studentService.getStudentByCode(code);
    }

    @GetMapping(path = "/studentsByProgram")
    public List<StudentResponse> getStudentByProgramId(@RequestParam(value = "programId") String programId){
        return studentService.getStudentByProgramId(programId);
    }

    @GetMapping(path = "students-by-program")
    public Map<String,List<StudentResponse>> studentByProgramId(){
        /*return studentRepository.findAll()
                .stream()
                .flatMap(Stream::ofNullable) //possible change it of flatmap (Stream::ofNullable)
                .collect(Collectors.groupingBy(Student::getProgramId));*/
        return studentService.studentByProgramId();

    }
}
