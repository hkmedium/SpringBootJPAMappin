package com.hkmedium.jpamapping.controller;

import com.hkmedium.jpamapping.dto.EmployeeDTO;
import com.hkmedium.jpamapping.entity.Department;
import com.hkmedium.jpamapping.entity.Employee;
import com.hkmedium.jpamapping.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllEmployeesByDepartmentId();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }
    @GetMapping("/departments/{departmentId}/employees-details")
    public ResponseEntity<?> getAllEmployeesDetailsByDepartmentId(@PathVariable int departmentId) {
        Set<Employee> employees = departmentService.getAllEmployeesByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/departments/{departmentId}/employees-query-dto")
    public ResponseEntity<?> findEmployeeDTOsByDepartmentId(@PathVariable int departmentId) {
        Set<EmployeeDTO> employees = departmentService.findEmployeeDTOsByDepartmentId(departmentId);
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/departments/{departmentId}/employees")
    public ResponseEntity<?> getAllEmployeesByDepartmentId(@PathVariable int departmentId) {
        List<EmployeeDTO> employees = departmentService.getAllEmployeesWithDepartmentName(departmentId);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/departments/{departmentId}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int departmentId, @RequestBody Department updatedDepartment) {
        Department department = departmentService.updateDepartment(departmentId, updatedDepartment);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/departments/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int departmentId) {
        String message = departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(message);
    }
}

