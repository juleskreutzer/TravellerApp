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
    double longitude;
    Map<String, Integer> keywords;
    String[] reviews;



    public Marker(String name, String snippet, double lat, double lng, Map<String, Integer> keywords, String[] reviews) {
        this.name = name;
        this.snippet = snippet;
        this.latitude = lat;
        this.longitude = lng;
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

        dic = new HashMap<>();
        dic.put("expensive", 2);
        dic.put("neat", 1);
        reviews = new String[]{"very nice sleeping rooms, it looked very neat!", "The food they brought to my room was really good! 10/10 would visit again"};
        markers.add(new Marker("Media Markt", "Een ideale winkel om uren lang bezig te zijn", 51.4426456, 5.4735338 , dic, reviews));

        dic = new HashMap<>();
        dic.put("cheap", 2);
        dic.put("dirty rooms", 6);
        dic.put("bad food", 1);
        reviews = new String[]{"very nice sleeping rooms, it looked very neat!", "The food they brought to my room was really good! 10/10 would visit again"};
        markers.add(new Marker("Hotel Stadshuis", "Een leuk hotel midden in het centrum, ideaal om midden in de stad te overnachten", 51.4359472, 5.4797984, dic, reviews));
    }

}
