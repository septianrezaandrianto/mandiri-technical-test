package com.technical.testmandiri.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidException extends RuntimeException {

    private Integer code;
    private String status;
    private String message;

    public InvalidException(String msg) {
        super(msg);
    }
}
