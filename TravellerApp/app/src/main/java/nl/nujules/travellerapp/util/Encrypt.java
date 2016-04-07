package nl.nujules.travellerapp.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by juleskreutzer on 07-04-16.
 */
public class Encrypt {

    private final static String PREFIX = "PTS4_JK_MW_FI_NL";

    public static String encryptString(String message) {
        int length = message.length();

        String temp = PREFIX + message;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(temp.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;

    }
}
