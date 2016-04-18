package nl.nujules.travellerapp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by juleskreutzer on 04-04-16.
 */
public class HttpRequest {

    private static final String SERVER_ADDRESS = "http://185.107.212.20:8050";

    public static void PostRequest() { }

    public static void GetRequest() { }


    /**
     * Call the login endpoint to create a authToken for the user.
     * @param email Email address the user is registered with
     * @param password Password the user is registered with
     * @return true if login succesfull, false if not
     */
    public static void Login(final String email, final String password) throws IllegalArgumentException {

        if(email.equals(""))
            throw new IllegalArgumentException("Email needs to be provided!");

        if(password.equals(""))
            throw new IllegalArgumentException("Password needs to be provided!");

        final boolean successful;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String endpoint = String.format("/user/login/%s/%s", email, password);
                    URL url = new URL(SERVER_ADDRESS + endpoint);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.connect();

                    int code = connection.getResponseCode();

                    if(code == 200) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());


                        String token = new Scanner(in, "UTF-8").next();
                        System.out.println("==============================================================" + token);

                        if (!token.equals("") || token != null) {
                            User.getInstance().setAuthToken(token);
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("Something went wrong, the URL was probably incorrect. This is our problem.");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("At this time, we can't reach the server. Please try again later.");
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }

            }
        });

        try {
            t.start();
        } catch(IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    public static boolean Register(final String username, final String email, final String password) throws IllegalArgumentException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String endpoint = String.format("/user/register/%s/%s/%s", email, password, username);
                    URL url = new URL(SERVER_ADDRESS + endpoint);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");

                    InputStream in = new BufferedInputStream(connection.getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("Something went wrong, the URL was probably incorrect. This is our problem.");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("At this time, we can't reach the server. Please try again later.");
                }
            }
        });

        t.start();

        return true;
    }
}
