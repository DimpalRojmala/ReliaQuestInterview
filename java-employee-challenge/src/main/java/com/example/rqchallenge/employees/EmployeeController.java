package com.example.rqchallenge.employees;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(
        name = "CRUD REST api for Employee resource",
        description = "CREATE User, UPDATE User, DELETE User, GET user, Get all user"
)
@RestController
@AllArgsConstructor
public class EmployeeController implements IEmployeeController{

    @Autowired
    IEmployeeService employeeService;

    @Operation(
            summary = "Get All Employee",
            description = "get all employee gives list of all employee"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees()  {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(
            summary = "Search Employee",
            description = "get all employee list for whom name match search string"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString){
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @Operation(
            summary = "Get Employee by Id",
            description = "get employee detail for id passed in path url"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(
            summary = "Get Highest Salary",
            description = "get highest salary for employee"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @Operation(
            summary = "Get Top Ten Highest Salaried Employee",
            description = "Get Top Ten Highest Earning Employee names"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @Operation(
            summary = "Create Employee",
            description = "Create Employee"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeInput), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete Employee",
            description = "Delete Employee by Id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}
