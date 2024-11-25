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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new department
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Get a department by ID
    public Department getDepartmentById(int depId) {
        return departmentRepository.findById(depId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + depId));
    }

    // Get all departments
    public List<Department> getAllEmployeesByDepartmentId() {
        return departmentRepository.findAll();
    }

    public Set<Employee> getAllEmployeesByDepartmentId(int departmentId) {
        return departmentRepository.findEmployeesByDepartmentId(departmentId);
    }

    public Set<EmployeeDTO> findEmployeeDTOsByDepartmentId(int departmentId) {
        return departmentRepository.findEmployeeDTOsByDepartmentId(departmentId);
    }

    @Transactional
    public List<EmployeeDTO> getAllEmployeesWithDepartmentName(int departmentId) {
        Set<Employee> employees = departmentRepository.findEmployeesByDepartmentId(departmentId);

        // Transform Employee entities to EmployeeDTOs
        return employees.stream()
                .map(employee -> new EmployeeDTO(
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getDepartment().getDepId(),
                        employee.getDepartment().getDepartmentName()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Department updateDepartment(int depId, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(depId);

        if (updatedDepartment.getDepartmentName() != null) {
            existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());
        }

        return departmentRepository.save(existingDepartment);
    }

    public String deleteDepartment(int depId) {
        Department department = getDepartmentById(depId);
        // Break the association explicitly
        for (Employee employee : department.getEmployees()) {
            employee.setDepartment(null); // Remove the link to the department
        }
        department.getEmployees().clear(); // Clear the collection in memory
        departmentRepository.save(department); // Persist changes
        // Now delete the department
        departmentRepository.delete(department);
        return "Success";
    }
}

