package org.example.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.utils.validator.ValidationError;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ValidateUserException extends Exception {
    List<ValidationError> errorList = new ArrayList<>();

    public ValidateUserException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        Iterator<ValidationError> iterator = errorList.iterator();
        while (iterator.hasNext()) {
            ValidationError err = iterator.next();
            sb.append(err.errorMessage());
            if (iterator.hasNext()) {
                sb.append("\n");
            }
        }

        if (CollectionUtils.isEmpty(errorList)) {
            return super.getMessage();
        }
        return MessageFormat.format(super.getMessage() + ":\n{0}", sb.toString());

    }
}
