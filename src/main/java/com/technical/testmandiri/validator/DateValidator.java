package com.technical.testmandiri.validator;


import com.technical.testmandiri.constant.Constant;
import com.technical.testmandiri.exception.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class DateValidator implements ConstraintValidator<DateValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        if(Objects.isNull(value) || ObjectUtils.isEmpty(value.trim()) || !value.matches(regex)) {
            throw InvalidException.builder()
                    .code(Constant.ERROR_422_CODE)
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                    .message(Constant.ERROR_422_MESSAGE.replace("{field_name}", "birth_date")
                            .replace("{value}", Objects.isNull(value) ? "null" : value))
                    .build();
        }

        return true;
    }
}
