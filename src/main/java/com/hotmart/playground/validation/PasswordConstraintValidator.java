package com.hotmart.playground.validation;

import com.google.common.base.Joiner;
import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.QwertySequenceRule;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
        // required method
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(6, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new NumericalSequenceRule(3, false),
                new AlphabeticalSequenceRule(3, false),
                new QwertySequenceRule(3, false)
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
