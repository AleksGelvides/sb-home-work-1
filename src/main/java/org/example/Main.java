package org.example;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.config.DefaultAppConfig;
import org.example.domain.User;
import org.example.domain.UserStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DefaultAppConfig.class);
        Scanner scanner = new Scanner(System.in);
        UserStorage storage = context.getBean(UserStorage.class);

        while (true) {
            System.out.println("\"all\" - получить всех пользователей");
            System.out.println("\"new ФИО;НОМЕР ТЕЛЕФОНА;ЕМАЙЛ\" - получить всех пользователей");
            System.out.println("\"del ЕМАЙЛ\" - удалить пользователя по email");
            System.out.print("Введите команду -> ");
            try {
                String command = scanner.nextLine();
                if (StringUtils.isNoneBlank(command)) {
                    if (command.equals("all")) {
                        printLine();
                        storage.printAll();
                        printLine();
                    } else if (command.startsWith("new")) {
                        printLine();
                        if (command.length() > 4) {
//                        Иванов Петр Сергеевич;+79231805350;petrov@gmial.com
                            User user = new User(command.substring(4));
                            boolean result = storage.saveUser(user);
                            if (BooleanUtils.isTrue(result)) System.out.println("Пользователь добавлен");
                        } else {
                            System.out.println(MessageFormat.format("Комманда {0} не полная", command));
                        }
                        printLine();
                    } else if (command.startsWith("del")) {
                        printLine();
                        if (command.length() > 4) {
                            String email = command.substring(4);
                            storage.removeUser(email);
                            System.out.println(MessageFormat.format("Пользователь с емайл {0} был найден и удален", email));
                        } else {
                            System.out.println(MessageFormat.format("Комманда {0} не полная", command));
                        }
                        printLine();
                    } else {
                        printLine();
                        System.out.println("Неизвестная команда.");
                        printLine();
                    }
                }
            } catch (Exception e) {
                System.out.print("Произошла ошибка: ");
                System.out.println(e.getMessage());
                printLine();
            }
        }
    }

    public static void printLine() {
        System.out.println("<----------------------------------------->");
    }
}
