package com.example.rqchallenge;

import com.example.rqchallenge.dto.AllEmployeeResponseDto;
import com.example.rqchallenge.dto.EmployeeResponseDto;
import com.example.rqchallenge.employees.Employee;
import com.example.rqchallenge.employees.EmployeeRepository;
import com.example.rqchallenge.employees.EmployeeService;
import com.example.rqchallenge.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class EmployeeServiceTest {


    @MockBean
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void getAllEmployees_should_return_employee_list() {
        //given
        AllEmployeeResponseDto allEmployeeResponseDto = this.buildTestingAllEmployeeResponseDto();
        //when
        when(employeeRepository.getAllEmployees())
                .thenReturn(allEmployeeResponseDto);
        List<Employee> allEmployees = employeeService.getAllEmployees();
        //then
        verify(employeeRepository).getAllEmployees();
        assertEquals(1,allEmployees.size());

    }
    @Test
    void getEmployeeById_should_return_employee() {

        EmployeeResponseDto employeeResponseDto = this.buildTestingEmployeeResponseDto();
        String employeeId = "1";

        when(employeeRepository.getEmployeeById(employeeId))
                .thenReturn(employeeResponseDto);
        Employee employee = employeeService.getEmployeeById(employeeId);

        verify(employeeRepository).getEmployeeById(employeeId);
        assertEquals(employeeResponseDto.getData().getId(),employee.getId());

    }
    @Test
    void getEmployeeById_negative_should_return_exception() {

        EmployeeResponseDto employeeResponseDto = this.buildTestingEmployeeResponseDto();
        employeeResponseDto.setData(null);
        String employeeId = "100";

        when(employeeRepository.getEmployeeById(employeeId))
                .thenReturn(employeeResponseDto);
        assertThrows(ResourceNotFoundException.class,
              ()->{
                employeeService.getEmployeeById(employeeId);
              });

    }

    @Test
    void createEmployee_should_create_employee() {

        Employee employee = this.buildTestingEmployee();
        HashMap<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("name","Dimpal Rojmala");
        employeeMap.put("salary",10000);
        employeeMap.put("age",30);

        when(employeeRepository.createEmployee(employeeMap))
                .thenReturn(employee);
        Employee createdEmployee = employeeService.createEmployee(employeeMap);

        verify(employeeRepository).createEmployee(employeeMap);
        assertEquals(createdEmployee.getEmployeeName(),employee.getEmployeeName());

    }
    @Test
    void deleteEmployee_should_delete_employee() {

        String employeeId="1";
        EmployeeResponseDto employeeResponseDto = this.buildTestingEmployeeResponseDto();

        when(employeeRepository.getEmployeeById(employeeId))
                .thenReturn(employeeResponseDto);
        when(employeeRepository.deleteEmployeeById(employeeId))
                .thenReturn("success");
        String employeeName = employeeService.deleteEmployeeById(employeeId);

        verify(employeeRepository).deleteEmployeeById(employeeId);
        assertEquals(employeeName,"Dimpal Rojmala");

    }



    private Employee buildTestingEmployee() {
        return   new Employee(1L,"Dimpal Rojmala",10000,30,"");
    }

    private AllEmployeeResponseDto buildTestingAllEmployeeResponseDto() {
        return   new AllEmployeeResponseDto("success",List.of(this.buildTestingEmployee()),"Successfully! ");
    }
    private EmployeeResponseDto buildTestingEmployeeResponseDto() {
        return   new EmployeeResponseDto("success",this.buildTestingEmployee(),"Successfully! ");
    }

}

