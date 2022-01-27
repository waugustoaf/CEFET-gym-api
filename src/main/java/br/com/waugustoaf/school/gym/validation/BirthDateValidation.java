package br.com.waugustoaf.school.gym.validation;

import br.com.waugustoaf.school.gym.validation.constraints.BirthDate;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthDateValidation implements ConstraintValidator<BirthDate, Date> {
    private Logger log = LoggerFactory.getLogger(BirthDateValidation.class);

    @Override
    public void initialize(BirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateLimit = sdf.parse("1902-01-01 00:00:00");

        return date.after(dateLimit);
    }
}
