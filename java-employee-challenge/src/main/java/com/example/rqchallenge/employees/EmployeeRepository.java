package com.example.rqchallenge.employees;

import com.example.rqchallenge.dto.AllEmployeeResponseDto;
import com.example.rqchallenge.dto.EmployeeResponseDto;
import com.example.rqchallenge.exception.ExternalHttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Repository
public class EmployeeRepository {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepository.class);

    @Value("${external.endpoint.employee.all}")
    private String externalAllEmployeeURL;

    @Value("${external.endpoint.employee}")
    private String externalEmployeeByIdURL;

    @Value("${external.endpoint.employee.create}")
    private String externalCreateEmployeeURL;

    @Value("${external.endpoint.employee.delete}")
    private String externalDeleteEmployeeURL;

    public AllEmployeeResponseDto getAllEmployees()
    {
        ResponseEntity<AllEmployeeResponseDto> allEmployeeData = this.executeExternalEndpoint(externalAllEmployeeURL,
                HttpMethod.GET,
                AllEmployeeResponseDto.class);
        return allEmployeeData.getBody();
    }

    public EmployeeResponseDto getEmployeeById(String employeeId)
    {
        ResponseEntity<EmployeeResponseDto> allEmployeeData = this.executeExternalEndpoint(externalEmployeeByIdURL+"/"+employeeId,
                HttpMethod.GET,
                EmployeeResponseDto.class);
        return allEmployeeData.getBody();
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        EmployeeResponseDto createEmployeeResponseDto = webClient.post()
                .uri(externalCreateEmployeeURL)
                .bodyValue(employeeInput)
                .retrieve()
                .bodyToMono(EmployeeResponseDto.class)
                .block();
        return createEmployeeResponseDto.getData();
    }

    public String deleteEmployeeById(String id) {

        ResponseEntity<String> responseEntity = this.executeExternalEndpoint(externalDeleteEmployeeURL+"/"+id,
                HttpMethod.DELETE,
                String.class);
        return responseEntity.getStatusCode().is2xxSuccessful() ?
                "success" : "fail";
    }

    public <T> ResponseEntity<T> executeExternalEndpoint(String url, HttpMethod httpMethod, Class<T> t)
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

    }
}
