package com.example.sample.exception;

import com.example.sample.dto.response.CommonResponseProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppError extends CommonResponseProperty {
    private Integer statusCode;
    private String errorMessage;
    private ZonedDateTime time;

    public AppError(String errorMessage, HttpStatus statusCode, ZonedDateTime time) {
        this.errorMessage = errorMessage;
        this.statusCode = statusCode.value();
        this.time = time;
    }
}
