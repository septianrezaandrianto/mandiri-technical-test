package com.technical.testmandiri.dto;

import com.technical.testmandiri.validator.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @SsnValidation
    private String ssn;
    @FirstNameValidation
    private String first_name;
    @MiddleNameValidation
    private String middle_name;
    @LastNameValidation
    private String last_name;
    @DateValidation
    private String birth_date;
    private String created_time;
    private String updated_time;
    private String created_by;
    private String updated_by;
    private Boolean is_active;
    private String deleted_time;

}
