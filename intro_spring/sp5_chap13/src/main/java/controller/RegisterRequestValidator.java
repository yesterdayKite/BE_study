package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.RegisterRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRequestValidator implements Validator {
    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public RegisterRequestValidator(){
        pattern = Pattern.compile(emailRegExp);
    }

    // 필수구현해야함
    // 파라미터로 전달받은 clazz 객체가 RegisterRequest 클래스로 타입 변환이 가능한지 확인한다.
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    // 필수구현해야함
    // target = 검사 대상 객체. errors = 검사 결과 에러코드를 설정하기 위한 객체
    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest regReq = (RegisterRequest) target;
        if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()){
            errors.rejectValue("email", "required");
        } else {
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if (!matcher.matches()){
                errors.rejectValue("email", "bad");
            }
        }
        // null이거나 whitespace일때 , 해당 에러코드를 추가함
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"confirmPassword", "required");
        if (!regReq.getPassword().isEmpty()){
            if (!regReq.getPassword().isEmpty()){
                if (!regReq.isPasswordEqualToConfirmPassword()){
                    errors.rejectValue("confirmPassword", "nomatch");
                }
            }
        }
    }
}
