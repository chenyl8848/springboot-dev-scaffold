package com.codechen.scaffold.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author：Java陈序员
 * @date 2023-06-16 11:43
 * @description 手机号校验器
 */
public class IsPhoneValidator implements ConstraintValidator<IsPhone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^1((34[0-8])|(8\\d{2})|(([35][0-35-9]|4[579]|66|7[35678]|9[1389])\\d{1}))\\d{7}$");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
