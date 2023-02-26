package com.technical.testmandiri.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface DateValidation {

    String message() default "Invalid field date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
