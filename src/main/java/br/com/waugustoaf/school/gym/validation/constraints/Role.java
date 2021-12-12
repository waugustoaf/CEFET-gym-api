package br.com.waugustoaf.school.gym.validation.constraints;

import br.com.waugustoaf.school.gym.validation.RoleValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {
    String message() default "Invalid user type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
