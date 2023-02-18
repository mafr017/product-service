package com.mafr.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseDTO<T> {

    public ResponseDTO(HttpStatus status, T data) {
        this.data = data;
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.httpStatus = status;
    }

    private String timestamp = LocalDateTime.now().toString();
    private T data;
    private Integer status;
    private String message;
    @JsonIgnore
    private HttpStatus httpStatus;
}

