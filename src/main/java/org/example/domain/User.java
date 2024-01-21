package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.exceptions.ValidateUserException;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String fullName;
    private String phoneNumber;
    private String email;

    public User(String userFromFile) throws ValidateUserException {
        try {
            String[] arr = userFromFile.split(";");
            this.fullName = arr[0] != null ? arr[0] : "";
            this.phoneNumber = arr[1] != null ? arr[1] : "";
            this.email = arr[2] != null ? arr[2] : "";
        } catch (Exception e) {
            throw new ValidateUserException("Некорректный ввод данных пользователя");
        }
    }
}
