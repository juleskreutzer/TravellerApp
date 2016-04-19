package nl.nujules.travellerapp.tasks;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by juleskreutzer on 19-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class loginTask extends AsyncTask<String, Void, String> {

    /**
     * Login a use with an AsyncTask to take the load of the main thread
     * @param params params use. We request 3 params: 1) URL (string), 2) email, 3) password (encrypted)
     * @return authtoken if login is successful
     */
    @Override
    protected String doInBackground(String... params) throws  IllegalArgumentException {

        int count = params.length;
        if (count != 3) {
            throw new IllegalArgumentException("Not enough or to much params provided!");
        }
        String authToken = null;
        String urlString = params[0];
        String email = params[1];
        if (!email.contains("@")) { throw new IllegalArgumentException("Email incorrect"); }

        String password = params[2];

        for (int i = 0; i < count; i++) {
            try {
                URL url = new URL(String.format("%s/user/login/%s/%s", urlString, email, password));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.connect();

                int code = connection.getResponseCode();

                if (code == 200) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());


                    authToken = new Scanner(in, "UTF-8").next();
                    System.out.println(String.format("Received authToken: %s", authToken));
                } else if (code == 400) {
                    throw new IllegalArgumentException("Email or password incorrect");
                }
                else {
                    throw new IllegalArgumentException(String.format("Something went wrong (response code %s)", code));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return authToken;

    }
}
