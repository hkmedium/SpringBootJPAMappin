package com.hkmedium.jpamapping.repository;

import com.hkmedium.jpamapping.dto.EmployeeDTO;
import com.hkmedium.jpamapping.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT new com.hkmedium.jpamapping.dto.EmployeeDTO(e.employeeId, e.employeeName, d.depId , d.departmentName) " +
            "FROM Employee e JOIN e.department d")
    List<EmployeeDTO> findAllEmployeesWithDepartmentName();
}
