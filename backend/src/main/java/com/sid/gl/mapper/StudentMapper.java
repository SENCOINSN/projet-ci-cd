package com.sid.gl.mapper;

import com.sid.gl.dto.StudentResponse;
import com.sid.gl.entities.Student;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentMapper {

    public static  StudentResponse toStudentResponse(Student student){
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getCode(),
                student.getProgramId(),
                student.getPhoto()
        );
    }

    public static List<StudentResponse> toListStudentResponse(List<Student> students){
        return students
                .stream()
                .filter(Objects::nonNull)
                .map(StudentMapper::toStudentResponse)
                .collect(Collectors.toList());
    }
}
