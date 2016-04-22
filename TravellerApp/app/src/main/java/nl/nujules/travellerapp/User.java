package nl.nujules.travellerapp;

import android.graphics.Bitmap;

/**
 * Created by juleskreutzer on 04-04-16.
 */
public class User {

    private String username = "";
    private String email = "";
    private String bio = "";
    private String firstName = "";
    private String lastName = "";
    private String country = "";
    private String proftilePictureUrl = "";
    private String authToken = "";
    private Bitmap imageBitmap = null;

    private static User _instance;

    public static User getInstance() {
        if(_instance == null) { new User(); }

        return _instance;
    }

    private User() { _instance = this; }

    // GETTERS

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getProftilePictureUrl() {
        return proftilePictureUrl;
    }

    public String getAuthToken() { return authToken; }

    public Bitmap getImageBitmap() { return imageBitmap; }

    // SETTERS

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProftilePictureUrl(String proftilePictureUrl) {
        this.proftilePictureUrl = proftilePictureUrl;
    }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public void setImageBitmap(Bitmap imageBitmap) { this.imageBitmap = imageBitmap; }


}
