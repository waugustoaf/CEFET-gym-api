package br.com.waugustoaf.school.gym.validation.constraints;

import br.com.waugustoaf.school.gym.validation.UserTypeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserTypeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserType {

    String message() default "Invalid user type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
