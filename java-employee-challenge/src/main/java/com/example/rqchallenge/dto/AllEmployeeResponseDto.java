package com.example.rqchallenge.dto;

import com.example.rqchallenge.employees.Employee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllEmployeeResponseDto {

        private String status;
        private List<Employee> data;
        private String message;

}
