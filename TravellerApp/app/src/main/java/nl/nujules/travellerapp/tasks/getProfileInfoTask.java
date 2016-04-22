package nl.nujules.travellerapp.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import nl.nujules.travellerapp.Profile;
import nl.nujules.travellerapp.User;
import nl.nujules.travellerapp.util.Base64Encoder;

/**
 * Created by juleskreutzer on 19-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class getProfileInfoTask extends AsyncTask<Object, Void, Boolean> {

    private Exception thrownException = null;
    private Profile profile;


    /**
     * Get the userinfo of the logged in user
     * @param params required params (array): 1) Class instance, 2) server address
     * @return
     */
    @Override
    protected Boolean doInBackground(Object... params) {
        int count = params.length;
        if(count != 2) { thrownException = new IllegalArgumentException("Not enough or too much params provided."); }

        profile = (Profile) params[0];
        String stringUrl = (String) params[1];

        String authToken = User.getInstance().getAuthToken();
        if(authToken.equals("")) { thrownException = new IllegalArgumentException("Please login to retrieve your information"); }

        try{
            URL url = new URL(String.format("%s/user/find/email/%s", stringUrl, Base64Encoder.encode(User.getInstance().getEmail())));
            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();


            if(code == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());

                StringBuilder buffer = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                } finally {
                    in.close();
                    reader.close();
                }

                String data = buffer.toString();

                JSONArray jsonArray = new JSONArray(data);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);

                    User.getInstance().setUsername(json.getString("username"));
                    User.getInstance().setBio(json.getString("bio"));
                    User.getInstance().setEmail(json.getString("email"));
                    User.getInstance().setProftilePictureUrl(json.getString("pictureUrl"));
                }


                if(!User.getInstance().getProftilePictureUrl().equals("")) {
                    URL url2 = new URL(String.format("%s", User.getInstance().getProftilePictureUrl()));
                    HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    InputStream input = new BufferedInputStream(conn.getInputStream());

                    StringBuilder buf = new StringBuilder();

                    BufferedReader rdr = new BufferedReader(new InputStreamReader(input));

                    String l;
                    try {
                        while ((l = rdr.readLine()) != null) {
                            buf.append(l);
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        User.getInstance().setImageBitmap(bitmap);
                    } finally {
                        input.close();
                        rdr.close();
                    }
                }

                return true;

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
            e.printStackTrace();
            thrownException = new IllegalArgumentException("Something went wrong with the data we got from the server. please try again");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(thrownException != null) {
            profile.showError(thrownException.getMessage());
        }

    }
}


