package ems_backend.service.impl;

import ems_backend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import ems_backend.dto.EmployeeDto;
import ems_backend.service.EmployeeService;
import ems_backend.repository.EmployeeRepository;
import ems_backend.dto.EmployeeResponse;
import ems_backend.entity.Employee;
import ems_backend.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee with id "+employeeId+" does not exist."));

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () ->
                        new ResourceNotFoundException("Employee with id "+employeeId+" does not exist.")
        );

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId).orElseThrow(
                () ->
                        new ResourceNotFoundException("Employee with id "+employeeId+" does not exist.")
        );

        employeeRepository.deleteById(employeeId);
    }

    @Override
    public EmployeeResponse getAllEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Configure the Sort object dynamically based on the direction requested
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //Create the Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Fetch the "Page" from the database.
        Page<Employee> employees = employeeRepository.findAll(pageable);

        //Extract the raw Entity data and map it to DTOs
        List<Employee> listOfEmployees = employees.getContent();
        List<EmployeeDto> content = listOfEmployees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .collect(Collectors.toList());

        //Build the EmployeeResponse object with the data AND the metadata
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setContent(content);
        employeeResponse.setPageNo(employees.getNumber());
        employeeResponse.setPageSize(employees.getSize());
        employeeResponse.setTotalElements(employees.getTotalElements());
        employeeResponse.setTotalPages(employees.getTotalPages());
        employeeResponse.setLast(employees.isLast());

        return employeeResponse;
    }
}
