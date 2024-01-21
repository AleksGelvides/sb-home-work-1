package org.example.domain;

import org.example.exceptions.ValidateUserException;
import org.example.utils.io.UserFileReadUtils;
import org.example.utils.validator.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ListIterator;

@Component
public class UserStorage extends ArrayList<User> implements Storage {
    @Value("${app.file}")
    private String filePath;
    @Value("${app.initFromFile}")
    private Boolean initFromFile;
    private final ResourceLoader resourceLoader;
    private final Validator validator;
    private File userFile;

    public UserStorage(ResourceLoader resourceLoader, Validator validator) {
        this.resourceLoader = resourceLoader;
        this.validator = validator;
    }

    @Override
    public void printAll() {
        this.stream()
                .map(user -> MessageFormat.format("{0} | {1} | {2}", user.getFullName(), user.getPhoneNumber(), user.getEmail()))
                .forEach(System.out::println);
    }

    @Override
    public Boolean saveUser(User user) throws ValidateUserException {
        validator.validationUser(user);
        UserFileReadUtils.writeNewUser(userFile, user);
        return this.add(user);
    }

    @Override
    public void removeUser(String email) throws Exception {
        ListIterator<User> iterator = this.listIterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equals(email)) {
                iterator.remove();
                UserFileReadUtils.rewriteFile(userFile, this);
                return;
            }
        }
        throw new Exception(MessageFormat.format("Пользователь с email {0} не найден и не был удален", email));
    }

    @PostConstruct
    private void initStorage() throws IOException {
        this.userFile = resourceLoader.getResource(filePath).getFile();
        if (initFromFile) {
            this.addAll(UserFileReadUtils.readFile(userFile));
        }
    }
}
