package com.sid.gl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
   @Column(unique = true)
    private String code;
    private String programId;
    private String photo;
}
