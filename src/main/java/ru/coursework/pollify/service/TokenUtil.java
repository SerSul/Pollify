package ru.coursework.pollify.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class TokenUtil {
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

    public boolean isTokenValid(String receivedToken, String storedTokenHash) {
        var hashedReceivedTokenHash = hash(receivedToken);
        return hashedReceivedTokenHash.equals(storedTokenHash) || receivedToken.equals(storedTokenHash);
    }


}
