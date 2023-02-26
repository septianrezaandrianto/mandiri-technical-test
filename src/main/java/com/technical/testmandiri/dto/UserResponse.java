package com.technical.testmandiri.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;
    private String ssn;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String birth_date;
    private String created_time;
    private String updated_time;
    private String created_by;
    private String updated_by;
    private Boolean is_active;
    private String deleted_time;

}
