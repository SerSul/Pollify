package ru.coursework.pollify.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Утилита для работы с токенами.
 * Предоставляет методы для хеширования токенов и проверки их валидности.
 */
@Service
public class TokenUtil {
    /**
     * Хеширует переданный токен с использованием алгоритма SHA-256.
     *
     * @param token Токен для хеширования.
     * @return Хешированный токен в шестнадцатеричном формате.
     * @throws RuntimeException Если возникает ошибка при получении алгоритма хеширования.
     */
    public String hash(String token) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(token.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing token", e);
        }
    }

    /**
     * Проверяет валидность полученного токена по сравнению с сохраненным хешем.
     *
     * @param receivedToken Токен, полученный от пользователя.
     * @param storedTokenHash Хешированный токен, сохраненный в системе.
     * @return true, если токен валиден; false в противном случае.
     */
    public boolean isTokenValid(String receivedToken, String storedTokenHash) {
        var hashedReceivedTokenHash = hash(receivedToken);
        return hashedReceivedTokenHash.equals(storedTokenHash) || receivedToken.equals(storedTokenHash);
    }


}
