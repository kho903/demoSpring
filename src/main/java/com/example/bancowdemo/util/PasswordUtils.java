package com.example.bancowdemo.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

    public static String encryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // 암호화한 패스워드 리턴
    public static boolean equalPassword(String password, String encryptedPassword) {
        try {
            return BCrypt.checkpw(password, encryptedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
