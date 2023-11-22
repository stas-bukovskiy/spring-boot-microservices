package com.recordsystem.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private Timestamp createdAt;

}
