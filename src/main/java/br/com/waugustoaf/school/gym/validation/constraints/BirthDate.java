package br.com.waugustoaf.school.gym.validation.constraints;

import br.com.waugustoaf.school.gym.validation.BirthDateValidation;
import br.com.waugustoaf.school.gym.validation.UserTypeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthDateValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDate {

    String message() default "Invalid birth date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
