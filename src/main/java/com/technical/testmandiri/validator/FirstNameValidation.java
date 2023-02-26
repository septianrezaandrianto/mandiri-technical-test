package com.technical.testmandiri.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FirstNameValidator.class)
public @interface FirstNameValidation {

    String message() default "Invalid field first_name format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
