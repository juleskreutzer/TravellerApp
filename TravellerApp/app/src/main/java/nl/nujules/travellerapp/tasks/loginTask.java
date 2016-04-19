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

import nl.nujules.travellerapp.DoLogin;

/**
 * Created by juleskreutzer on 19-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class loginTask extends AsyncTask<Object, Void, String> {

    private Exception thrownException = null;
    private DoLogin doLogin = null;

    /**
     * Login a use with an AsyncTask to take the load of the main thread
     * @param params params use. We request 3 params: 1) Class instance 2) URL (string), 3) email, 4) password (encrypted)
     * @return authtoken if login is successful
     */
    @Override
    protected String doInBackground(Object... params) throws  IllegalArgumentException {

        int count = params.length;
        if (count != 4) {
            thrownException = new IllegalArgumentException("Not enough or to much params provided!");
        }
        String authToken = null;

        doLogin = (DoLogin) params[0];
        String urlString = (String) params[1];
        String email = (String) params[2];
        if (!email.contains("@")) { thrownException = new IllegalArgumentException("Email incorrect"); }

        String password = (String) params[3];

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
                    thrownException = new IllegalArgumentException("Email or password incorrect");
                }
                else {
                    thrownException = new IllegalArgumentException(String.format("Something went wrong (response code %s)", code));
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

    @Override
    public void onPostExecute(String result) {
        if(thrownException != null) {
            doLogin.showError(thrownException.getMessage());
        }
    }
}
