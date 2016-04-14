package nl.nujules.travellerapp.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by juleskreutzer on 14-04-16.
 */
public class Base64Encoder {

    public static String encode(String message) {
        byte[] byteArray = null;

        try {
            byteArray = message.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
