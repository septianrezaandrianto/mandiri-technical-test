package com.technical.testmandiri.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConflictException extends RuntimeException {

    private Integer code;
    private String status;
    private String message;

    public ConflictException(String msg) {
        super(msg);
    }
}
