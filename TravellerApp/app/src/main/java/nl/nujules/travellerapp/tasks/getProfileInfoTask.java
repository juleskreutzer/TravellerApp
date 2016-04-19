package nl.nujules.travellerapp.tasks;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import nl.nujules.travellerapp.User;
import nl.nujules.travellerapp.util.Base64Encoder;

/**
 * Created by juleskreutzer on 19-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class getProfileInfoTask extends AsyncTask<Object, Void, Void> {

    private Exception thrownException;


    /**
     * Get the userinfo of the logged in user
     * @param params required params (array): 1) Class instance, 2) server address
     * @return
     */
    @Override
    protected Void doInBackground(Object... params) {
        int count = params.length;
        if(count != 2) { thrownException = new IllegalArgumentException("Not enough or too much params provided."); }

        String stringUrl = (String) params[1];

        String authToken = User.getInstance().getAuthToken();
        if(authToken.equals("")) { thrownException = new IllegalArgumentException("Please login to retrieve your information"); }

        try{
            URL url = new URL(String.format("%s/user/find/email/%s", stringUrl, Base64Encoder.encode(User.getInstance().getEmail())));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", authToken);
            connection.connect();

            int code = connection.getResponseCode();

            if(code == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());


                String data = new Scanner(in, "UTF-8").next();
                JSONObject json = new JSONObject(data);

                User.getInstance().setUsername(json.getString("username"));
                User.getInstance().setBio(json.getString("bio"));
                User.getInstance().setEmail(json.getString("email"));
                User.getInstance().setProftilePictureUrl(json.getString("pictureUrl"));

            }
            else if(code == 400) {
                thrownException = new IllegalArgumentException("Please try again");

            }
            else if(code == 500) {
                thrownException = new IllegalArgumentException("Something went wrong. Please try again");

            }
            else {
                thrownException = new IllegalArgumentException("Oh-ooh! The server messed up. Please try again later.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            thrownException = new IllegalArgumentException("Something went wrong with the data we got from the server. please try again");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}


