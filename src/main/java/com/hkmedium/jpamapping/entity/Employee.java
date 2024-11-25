package com.hkmedium.jpamapping.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Data
@Getter
@Setter

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    private int employeeId;

    @Column(name = "employee_name", length = 45, nullable = false)
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "depId")
    private Department department;
}

