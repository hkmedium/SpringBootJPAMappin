package com.hkmedium.jpamapping.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO {
    private int employeeId;
    private String employeeName;
    private int departmentId;
    private String departmentName;

    public EmployeeDTO(int employeeId, String employeeName, String departmentName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.departmentName = departmentName;
    }
}
