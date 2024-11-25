package com.hkmedium.jpamapping.controller;

import com.hkmedium.jpamapping.dto.EmployeeDTO;
import com.hkmedium.jpamapping.entity.Employee;
import com.hkmedium.jpamapping.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PostMapping("/departments/{departmentId}/employees")
    public ResponseEntity<Employee> createEmployee(@PathVariable int departmentId, @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.addEmployee(departmentId, employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees-details")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesWithDepartment() {
        List<EmployeeDTO> employees = employeeService.getAllEmployeesWithDepartmentName();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        String message = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(message);
    }
}

