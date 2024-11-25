package com.hkmedium.jpamapping.service;

import com.hkmedium.jpamapping.dto.EmployeeDTO;
import com.hkmedium.jpamapping.entity.Department;
import com.hkmedium.jpamapping.entity.Employee;
import com.hkmedium.jpamapping.repository.DepartmentRepository;
import com.hkmedium.jpamapping.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee addEmployee(int departmentId, Employee employee) {
        // Fetch the department by ID
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found!"));

        // Set the department for the employee
        employee.setDepartment(department);
        // Save the employee (JPA automatically maintains the bidirectional relationship if configured properly)
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<EmployeeDTO> getAllEmployeesWithDepartmentName() {
        return employeeRepository.findAllEmployeesWithDepartmentName();
    }

    @Transactional
    public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(employeeId);

        if (updatedEmployee.getEmployeeName() != null) {
            existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        }
        if (updatedEmployee.getDepartment() != null) {
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
        }

        return employeeRepository.save(existingEmployee);
    }

    public String deleteEmployee(int employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employeeRepository.delete(employee);
        return "Employee deleted successfully";
    }
}

