package nl.nujules.travellerapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import nl.nujules.travellerapp.tasks.fetchKeywordsTask;

/**
 * Created by juleskreutzer on 18-04-16.
 */
public class Keywords {

    private static Keywords _instance;
    private ArrayList<String> keywords;
    private static final String SERVER_ADDRESS = "http://185.107.212.20:8050";

    private Keywords() {
        _instance = this;
        keywords = new ArrayList<>();
    }

    public static Keywords getInstance() {
        if(_instance == null) { new Keywords(); }

        return _instance;
    }

    public ArrayList<String> getKeywords() throws MalformedURLException {
        if(keywords.size() < 1) {
            // ArrayList keywords doesn't contain any keywords. Fetch them from the server.
            AsyncTask<String, Void, ArrayList<String>> temp = new fetchKeywordsTask().execute(String.format("%s/search/keyword/all", SERVER_ADDRESS));
            try {
                keywords.addAll(temp.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return keywords;

    }
}
