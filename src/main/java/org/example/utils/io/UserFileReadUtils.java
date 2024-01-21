package org.example.utils.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.example.domain.User;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class UserFileReadUtils {

    public static void rewriteFile(File file, List<User> users) {
        try {
            FileUtils.writeStringToFile(file, "", "UTF-8");
            users.forEach(user -> writeNewUser(file, user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<User> readFile(File file) {
        List<User> result = new ArrayList<>();
        try (LineIterator lineIterator = FileUtils.lineIterator(file)) {
            while (lineIterator.hasNext()) {
                result.add(new User(lineIterator.next()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void writeNewUser(File file, User user) {
        String str = MessageFormat.format("{0};{1};{2}",
                user.getFullName(), user.getPhoneNumber(), user.getEmail());
        try {
            FileUtils.writeStringToFile(file, str, "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
