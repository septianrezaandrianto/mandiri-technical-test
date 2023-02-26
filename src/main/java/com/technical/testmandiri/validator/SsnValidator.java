package com.technical.testmandiri.validator;

import com.technical.testmandiri.constant.Constant;
import com.technical.testmandiri.entity.User;
import com.technical.testmandiri.exception.ConflictException;
import com.technical.testmandiri.exception.InvalidException;
import com.technical.testmandiri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class SsnValidator implements ConstraintValidator<SsnValidation, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {

        if(Objects.isNull(value) || ObjectUtils.isEmpty(value.trim())) {
            throw InvalidException.builder()
                    .code(Constant.ERROR_422_CODE)
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                    .message(Constant.ERROR_422_MESSAGE.replace("{field_name}", "ssn")
                            .replace("{value}", Objects.isNull(value) ? "null" : value))
                    .build();
        } else if(!Objects.isNull(value)) {
            String regex = "\\d+";
            String ssn = (String.format("%16s",value).replace(" ", "0"));
            User user = userRepository.findBySsn(ssn);
            if(user != null) {
                throw ConflictException.builder()
                        .code(Constant.ERROR_409_CODE)
                        .status(HttpStatus.CONFLICT.getReasonPhrase())
                        .message(Constant.ERROR_409_MESSAGE.replace("{value}", ssn))
                        .build();
            } else if (value.length() > 16 || !value.matches(regex)) {
                throw InvalidException.builder()
                        .code(Constant.ERROR_422_CODE)
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .message(Constant.ERROR_422_MESSAGE.replace("{field_name}", "ssn")
                                .replace("{value}", value))
                        .build();
            }
        }
        return true;
    }
}
