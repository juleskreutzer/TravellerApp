package nl.nujules.travellerapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martijn on 15-4-2016.
 */
public class Marker {
    static public ArrayList<Marker> markers = new ArrayList<>();
    String name;
    String snippet;
    double latitude;
    double longtitude;
    Map<String, Integer> keywords;
    String[] reviews;

    public Marker(String name, String snippet, double lat, double lng, Map<String, Integer> keywords, String[] reviews) {
        this.name = name;
        this.snippet = snippet;
        this.latitude = lat;
        this.longtitude = lng;
        this.keywords = keywords;
        this.reviews = reviews;
    }

    static public void setUpMarkers() {
        Map<String, Integer> dic = new HashMap<>();
        dic.put("cheap", 5);
        dic.put("good customer care", 2);
        dic.put("neat", 3);
        String[] reviews = new String[]{"very nice sleeping rooms, it looked very neat!", "The food they brought to my room was really good! 10/10 would visit again"};
        markers.add(new Marker("Hotel Rabobank", "Een mooi hotel wat gebouwd is in 1985 en het staat als een huis.", 51.444880, 5.477443 , dic, reviews));
    }

}
