package nl.nujules.travellerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import nl.nujules.travellerapp.util.ActivityType;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final ActivityType activity = (ActivityType) getIntent().getSerializableExtra("activity");

        Button closeButton = (Button) findViewById(R.id.btnMenuClose);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                    switch(activity)
                    {
                        case MAIN_ACTIVITY:
                            intent = new Intent(Menu.this, MainActivity.class);
                            break;
                        case DO_LOGIN:
                            intent = new Intent(Menu.this, DoLogin.class);
                            break;
                        case DO_SIGNUP:
                            intent = new Intent(Menu.this, DoSignup.class);
                            break;
                        case LOGIN:
                            intent = new Intent(Menu.this, Login.class);
                            break;
                        case NEARBY:
                            intent = new Intent(Menu.this, Nearby.class);
                            break;
                        case NEARBY_RESULT:
                            intent = new Intent(Menu.this, NearbyResult.class);
                            break;
                        case ADD_REVIEW:
                            intent = new Intent(Menu.this, AddReview.class);
                            break;
                        default:
                            intent = new Intent(Menu.this, MainActivity.class);
                    }

                if(intent != null) {
                    startActivity(intent);
                }
            }
        });

        /**
         * Get buttons and add a onClickListener to it
         */

        Button btnShowPlace = (Button) findViewById(R.id.btnSearch);
        Button btnNearby = (Button) findViewById(R.id.btnNearby);
        Button btnAddKeyword = (Button) findViewById(R.id.btnAddKeyword);
        Button btnAddReview = (Button) findViewById(R.id.btnAddReview);
        Button btnProfile = (Button) findViewById(R.id.btnProfile);
        Button btnLogin = (Button) findViewById(R.id.btnMenuLogin);
        Button btnRegister = (Button) findViewById(R.id.btnMenuRegister);

        btnShowPlace.setOnClickListener(showSearchPlace());
        btnNearby.setOnClickListener(showNearby());
        btnAddKeyword.setOnClickListener(showAddKeyword());
        btnAddReview.setOnClickListener(showAddReview());
        btnProfile.setOnClickListener(showProfile());
        btnLogin.setOnClickListener(showLogin());
        btnRegister.setOnClickListener(showRegister());
    }

    /**
     * Create the onClickListener to show an activity to search for places
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showSearchPlace() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change the intent so it will display the search place activity
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        };

    }

    /**
     * Create the onClickListener to show an activity for nearby places
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showNearby() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change the intent so it will display the nearby activity
                Intent intent = new Intent(Menu.this, Nearby.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Create the onClickListener to show an activity to add a keyword to a marker
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showAddKeyword() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change the intent so it will display the add keyword activity
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Create the onClickListener to show an activity to add a review to a marker
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showAddReview() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change the intent so it will display the add review activity
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Create the onClickListener to show an activity which displays info about the logged in user
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showProfile() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change the intent so it will display the profile activity. Perhaps also check if the user is logged in and if not show the login activity
                Intent intent = new Intent(Menu.this, MainActivity.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Create the onClickListener to show an activity which displays the login form
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DoLogin.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Create the onClickListener to show an activity which displays the registration form
     * @return onClickListener that can be added to a button
     */
    private View.OnClickListener showRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DoSignup.class);
                startActivity(intent);
            }
        };
    }
}
