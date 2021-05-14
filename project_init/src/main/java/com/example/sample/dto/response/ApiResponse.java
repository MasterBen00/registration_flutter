package com.example.sample.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResponse<T> extends CommonResponseProperty {
    private Integer httpStatusCode;
    private Boolean success;
    private T responseData;

    public ApiResponse(T responseData, HttpStatus code, Boolean success) {
        this.httpStatusCode = code.value();
        this.responseData = responseData;
        this.success = success;
    }
}
