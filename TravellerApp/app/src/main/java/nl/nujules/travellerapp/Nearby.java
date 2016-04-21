package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import nl.nujules.travellerapp.util.ActivityType;

public class Nearby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace "Placeholder.this" with the class name you implement this is.
                Intent intent = new Intent(Nearby.this, Menu.class);
                intent.putExtra("activity", ActivityType.NEARBY);

                startActivity(intent);
            }
        });

        ImageButton restaurant = (ImageButton) findViewById(R.id.btnNearbyRestaurant);
        ImageButton shop = (ImageButton) findViewById(R.id.btnNearbyShop);
        ImageButton hotel = (ImageButton) findViewById(R.id.btnNearbyHotel);
        ImageButton leisure = (ImageButton) findViewById(R.id.btnNearbyLeisure);

        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nearby.this, NearbyResult.class);
                startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nearby.this, NearbyResult.class);
                startActivity(intent);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nearby.this, NearbyResult.class);
                startActivity(intent);
            }
        });

        leisure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nearby.this, NearbyResult.class);
                startActivity(intent);
            }
        });
    }
}
