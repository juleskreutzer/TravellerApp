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

import nl.nujules.travellerapp.DoSignup;

/**
 * Created by juleskreutzer on 19-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class registerTask extends AsyncTask<Object, Void, Boolean> {

    private Exception thrownException = null;
    DoSignup doSignup = null;

    /**
     * Register a user with this task to take the load of the main thread.
     * @param params Array containing the info for the user and url: 1)Class instance, 2) url, 3) email, 4) password (encrypted), 5) username
     * @return
     */
    @Override
    protected Boolean doInBackground(Object... params) {
        int count = params.length;
        if(count != 5) { throw new IllegalArgumentException("Not enough or too much params provided."); }

        doSignup = (DoSignup) params[0];
        String stringUrl = (String) params[1];
        String email = (String) params[2];
        String password = (String) params[3];
        String username = (String) params[4];

        try {
            URL url = new URL(String.format("%s/user/register/%s/%s/%s", stringUrl, email, password, username));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            int code = connection.getResponseCode();

            if(code == 200) {
                return true;
            }
            else if(code == 400) {
                InputStream in = new BufferedInputStream(connection.getInputStream());


                String data = new Scanner(in, "UTF-8").next();
                if(data == "5006") {
                    thrownException = new IllegalArgumentException("Email already exists");
                }
                thrownException =  new IllegalArgumentException("Something went wrong. Please try again");
            }
            else if(code == 500) {
                thrownException =  new IllegalArgumentException("Oh-ooh, a server error occurred.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(thrownException != null) {
            doSignup.showError(thrownException.getMessage());
        }

    }
}
