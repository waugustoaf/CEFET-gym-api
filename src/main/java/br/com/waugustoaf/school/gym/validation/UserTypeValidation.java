package br.com.waugustoaf.school.gym.validation;

import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.validation.constraints.UserType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserTypeValidation implements ConstraintValidator<UserType, User.UserType> {

    @Override
    public void initialize(UserType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User.UserType userTypeModel, ConstraintValidatorContext constraintValidatorContext) {
        return userTypeModel == User.UserType.administrator
                || userTypeModel == User.UserType.employee
                || userTypeModel == User.UserType.client;
    }
}
