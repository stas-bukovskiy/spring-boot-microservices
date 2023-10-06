package com.recordsystem.enrollment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@NoArgsConstructor
@Table("faculties")
public class Faculty {

    @Id
    private Long id;
    private String name;
    private String description;

}
