package ems_backend.service;

import ems_backend.dto.EmployeeDto;
import ems_backend.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);

    //Instead of returning List<EmployeeDto>, we return our new metadata wrapper
    EmployeeResponse getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteEmployee(Long employeeId);
}
