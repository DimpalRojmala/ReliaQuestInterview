package com.example.rqchallenge.employees;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEmployeeService {


    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByNameSearch(String searchString);

    Employee getEmployeeById(String employeeId);

    Integer getHighestSalaryOfEmployees();

    List<String> getTopTenHighestEarningEmployeeNames();

    Employee createEmployee(Map<String, Object> employeeInput);

    String deleteEmployeeById(String id);
}
