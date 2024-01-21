package org.example.domain;


import org.example.exceptions.ValidateUserException;

public interface Storage {

    void printAll();
    Boolean saveUser(User user) throws ValidateUserException;

    void removeUser(String email) throws Exception;
}
