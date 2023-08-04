package com.example.rqchallenge.dto;

import com.example.rqchallenge.employees.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    private String status;
    private Employee data;
    private String message;
}
