package com.example.rqchallenge.employees;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

        Long id;
        @JsonAlias({"employee_name","name"})
        String employeeName;
        @JsonAlias({"employee_salary","salary"})
        Integer employeeSalary;
        @JsonAlias({"employee_age","age"})
        Integer employeeAge;
        @JsonAlias("profile_image")
        String profileImage;
}
