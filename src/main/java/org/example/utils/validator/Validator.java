package org.example.utils.validator;

import org.apache.commons.lang3.StringUtils;
import org.example.domain.User;
import org.example.exceptions.ValidateUserException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class Validator {
    private static final String NAME_REGEXP = "^[а-яА-ЯёЁa-zA-Z]+\\s[а-яА-ЯёЁa-zA-Z]+\\s[а-яА-ЯёЁa-zA-Z]+$";
    private static final String PHONE_REGEXP = "\\+[0-9][0-9]{10}";
    private static final String EMAIL_REGEXP = "^[\\w|\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";


    public void validationUser(User user) throws ValidateUserException {
        List<ValidationError> errors = new ArrayList<>();
        if (StringUtils.isNoneBlank(user.getFullName()) && !user.getFullName().matches(NAME_REGEXP)) {
            errors.add(new ValidationError("ФИО не может быть пустым и должно иметь вид: Фамилия Имя Отчество"));
        }

        if (StringUtils.isNoneBlank(user.getPhoneNumber()) && !user.getPhoneNumber().matches(PHONE_REGEXP)) {
            errors.add(new ValidationError("Номер телефона не может быть пустым и должен быть описан в следующем формате: +00000000000"));
        }

        if (StringUtils.isNoneBlank(user.getEmail()) && !user.getEmail().matches(EMAIL_REGEXP)) {
            errors.add(new ValidationError("Некорректный email"));
        }

        if (!CollectionUtils.isEmpty(errors)) {
            ValidateUserException error = new ValidateUserException("Обнаружены ошибки валидации");
            error.setErrorList(errors);
            throw error;
        }
    }
}
