package com.technical.testmandiri.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MiddleNameValidator.class)
public @interface MiddleNameValidation {

    String message() default "Invalid field middle_name format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
