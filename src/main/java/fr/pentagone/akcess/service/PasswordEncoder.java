package fr.pentagone.akcess.service;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Component
public class PasswordEncoder {
    private static final String ALGORITHM = "SHA-256";

    public byte[] encode(String pwd){
        try {
            var digest = MessageDigest.getInstance(ALGORITHM);
            return digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException e){
            return new byte[0];
        }
    }
    public boolean verify(String from, byte[] origin){
        try {
            var digest = MessageDigest.getInstance(ALGORITHM);
            return Arrays.equals(origin, digest.digest(from.getBytes(StandardCharsets.UTF_8)));
        }catch (NoSuchAlgorithmException e){
            return false;
        }
    }
}
