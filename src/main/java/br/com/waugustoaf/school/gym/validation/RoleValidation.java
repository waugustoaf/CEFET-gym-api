package br.com.waugustoaf.school.gym.validation;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.validation.constraints.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidation implements ConstraintValidator<Role, User.Role> {

    @Override
    public void initialize(Role constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User.Role userTypeModel, ConstraintValidatorContext constraintValidatorContext) {
        return userTypeModel == User.Role.administrator
                || userTypeModel == User.Role.employee
                || userTypeModel == User.Role.client;
    }
}
