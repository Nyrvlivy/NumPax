package br.com.numpax.application.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordValidatorUtil {

    public static boolean isValid(String password) {

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.!?])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
