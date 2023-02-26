package com.technical.testmandiri.validator;

import com.technical.testmandiri.constant.Constant;
import com.technical.testmandiri.exception.InvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MiddleNameValidator implements ConstraintValidator<MiddleNameValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        String regex = "[a-zA-Z0-9]+$";
        if(Objects.isNull(value) || ObjectUtils.isEmpty(value.trim()) || value.length() < 3
                || value.length() > 100 || !value.matches(regex)) {
            throw InvalidException.builder()
                    .code(Constant.ERROR_422_CODE)
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                    .message(Constant.ERROR_422_MESSAGE.replace("{field_name}", "middle_name")
                            .replace("{value}", Objects.isNull(value) ? "null" : value))
                    .build();
        }
        return true;
    }
}
