package com.example.rqchallenge.employees;

import com.example.rqchallenge.dto.AllEmployeeResponseDto;
import com.example.rqchallenge.dto.EmployeeResponseDto;
import com.example.rqchallenge.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {

        AllEmployeeResponseDto allEmployeesResponseBody = employeeRepository.getAllEmployees();
        return allEmployeesResponseBody.getData();
    }
    @Override
    public Employee getEmployeeById(String employeeId) {
        EmployeeResponseDto employeeResponseBody = employeeRepository.getEmployeeById(employeeId);
        Optional<Employee> employee = Optional.ofNullable(employeeResponseBody.getData());
        return  employee
                .orElseThrow(() ->  new ResourceNotFoundException("Employee", "Employee Id", employeeId));
    }

    @Override
    public Employee createEmployee(Map<String, Object> employeeInput) {

        return employeeRepository.createEmployee(employeeInput);
    }

    @Override
    public String deleteEmployeeById(String id) {

        Employee employeeById = getEmployeeById(id);
        String status = employeeRepository.deleteEmployeeById(id);
        return status.equals("success") ?
                employeeById.getEmployeeName() : "Failed to delete employee with id : " + id;
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {

        List<Employee> allEmployees = getAllEmployees();

        return allEmployees
                .stream()
                .filter(employee -> employee.getEmployeeName().toUpperCase().contains(searchString.toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        List<Employee> allEmployees = getAllEmployees();
        return allEmployees
                .stream()
                .map(Employee::getEmployeeSalary)
                .max(Integer::compare)
                .orElseThrow();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> allEmployees = getAllEmployees();
        List<Employee> topTenHighestEarningEmployee = allEmployees
                .stream()
                .sorted(Comparator.comparing(Employee::getEmployeeSalary).reversed())
                .limit(10)
                .collect(Collectors.toList());
        return topTenHighestEarningEmployee.stream().map(Employee::getEmployeeName).collect(Collectors.toList());
    }

    /*public <T> ResponseEntity<T>  executeExternalEndpoint(String url,HttpMethod httpMethod,Class<T> t)
    {
        ResponseEntity<T> responseEntity;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            LOGGER.debug("Connecting external endpoint " + url);
            responseEntity = restTemplate.exchange(url, httpMethod, entity, t);
            LOGGER.debug("Received response from external endpoint " + url);
        }
        catch (HttpClientErrorException httpClientErrorException)
        {
            LOGGER.error(httpClientErrorException.getMessage());
            throw new ExternalHttpClientErrorException("Too Many Request : Try after some time !");
        }
        return  responseEntity;

    }*/


}
