package com.example.rqchallenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetail {

    LocalDateTime localDateTime;
    String message;
    String code;
    String path;

}
