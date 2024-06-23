package com.sid.gl.services;

import com.sid.gl.dto.StudentResponse;
import com.sid.gl.entities.Student;
import com.sid.gl.mapper.StudentMapper;
import com.sid.gl.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<StudentResponse> allStudents(){
        return StudentMapper.toListStudentResponse(studentRepository.findAll());
    }

    public StudentResponse getStudentByCode(String code){
        Student student = studentRepository.findByCode(code);
        if(student !=null){
            return StudentMapper.toStudentResponse(student);
        }
        return null;
    }

    public List<StudentResponse> getStudentByProgramId(String programId){
        return StudentMapper.toListStudentResponse(studentRepository.findByProgramId(programId));
    }

    public Map<String,List<StudentResponse>> studentByProgramId(){
        return studentRepository.findAll()
                .stream()
                .flatMap(Stream::ofNullable)
                .map(StudentMapper::toStudentResponse)
                .collect(Collectors.groupingBy(StudentResponse::programId));
    }
}
