package com.example.rqchallenge;

import com.example.rqchallenge.employees.Employee;
import com.example.rqchallenge.employees.EmployeeController;
import com.example.rqchallenge.employees.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WebClient webClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_employee_list() throws Exception {
        Employee employee = this.buildTestingEmployee();
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].employeeName", is("Dimpal Rojmala")))
                .andExpect(jsonPath("$[0].employeeSalary", is(10000)))
                .andExpect(jsonPath("$[0].employeeAge", is(30)))
                .andExpect(jsonPath("$[0].profileImage", is("")));
    }

    @Test
    void should_return_employee() throws Exception {
        Employee employee = this.buildTestingEmployee();
        when(employeeService.getEmployeeById("1")).thenReturn(employee);

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Dimpal Rojmala")))
                .andExpect(jsonPath("$.employeeSalary", is(10000)))
                .andExpect(jsonPath("$.employeeAge", is(30)))
                .andExpect(jsonPath("$.profileImage", is("")));
    }

    @Test
    void should_return_employee_search_list() throws Exception {
        Employee employee = this.buildTestingEmployee();
        when(employeeService.getEmployeesByNameSearch("dim")).thenReturn(List.of(employee));

        mockMvc.perform(get("/search/dim"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].employeeName", is("Dimpal Rojmala")))
                .andExpect(jsonPath("$[0].employeeSalary", is(10000)))
                .andExpect(jsonPath("$[0].employeeAge", is(30)))
                .andExpect(jsonPath("$[0].profileImage", is("")));
    }
    @Test
    void should_return_highest_salary() throws Exception {

        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(10000);

        mockMvc.perform(get("/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(10000)));

    }

    @Test
    void should_return_top_ten_highest_earning_employee() throws Exception {
        when(employeeService.getTopTenHighestEarningEmployeeNames())
                .thenReturn(List.of("Paul Byrd",
                        "Yuri Berry",
                        "Charde Marshall",
                        "Cedric Kelly",
                        "Tatyana Fitzpatrick",
                        "Brielle Williamson",
                        "Jenette Caldwell",
                        "Quinn Flynn",
                        "Rhona Davidson",
                        "Tiger Nixon"));

        mockMvc.perform(get("/topTenHighestEarningEmployeeNames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

    }
    @Test
    void should_create_employee() throws Exception {
        Employee employee = this.buildTestingEmployee();
        HashMap<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("name","Dimpal");
        employeeMap.put("salary",10000);
        employeeMap.put("age",30);
        when(employeeService.createEmployee(employeeMap))
                .thenReturn(employee);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test\",\"salary\": \"123\",\"age\": \"23\"}"))
                .andExpect(status().isCreated());

    }

    @Test
    void should_delete_employee() throws Exception {

        when(employeeService.deleteEmployeeById("1"))
                .thenReturn("Dimpal Rojmala");

        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dimpal Rojmala"));

    }


    private Employee buildTestingEmployee() {
      return   new Employee(1L,"Dimpal Rojmala",10000,30,"");
    }
}
