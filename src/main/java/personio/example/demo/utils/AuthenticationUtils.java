package personio.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import personio.example.demo.dao.AuthenticationSQLDao;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationUtils {

    @Autowired
    AuthenticationSQLDao authenticationDao;

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static String encrypt(String input) throws NoSuchAlgorithmException {
        return toHexString(getSHA(input));
    }

}
