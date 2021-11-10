package br.com.waugustoaf.school.gym.validation;

import br.com.waugustoaf.school.gym.validation.constraints.BirthDate;
import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthDateValidation implements ConstraintValidator<BirthDate, Date> {

    @Override
    public void initialize(BirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateLimit = sdf.parse("2001-01-01 00:00:00");

        return date.after(dateLimit);
    }
}
