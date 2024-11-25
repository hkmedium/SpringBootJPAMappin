package com.hkmedium.jpamapping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor

public class StudentDTO {
    private int studentId;
    private String studentName;
}
