package com.technical.testmandiri.validator;

import javax.servlet.annotation.HttpConstraint;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SsnValidator.class)
public @interface SsnValidation {

    String message() default "Invalid field ssn format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
