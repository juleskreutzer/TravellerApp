package nl.nujules.travellerapp.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by juleskreutzer on 18-04-16.
 * You're now reading the amazing copyright shizzle!
 */
public class fetchKeywordsTask extends AsyncTask<String, Void, ArrayList<String> > {

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        int UrlAmount = params.length;
        JSONObject object = null;

        // For each URL in params, execute the method
        for(int i = 0; i < UrlAmount; i++) {
                try {
                    URL url = new URL(params[i]);
                    InputStream is = url.openStream();
                    try {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                        String jsonText = readAll(rd);
                        object = new JSONObject(jsonText);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        is.close();
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }

        // Check if JSONObject object isn't null;
        if(object != null) {
            Iterator<String> iterator = object.keys();
            ArrayList<String> keywords = new ArrayList<>();

            while(iterator.hasNext()) {
                keywords.add(iterator.next());
            }

            return keywords;
        } else {
            return null;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
