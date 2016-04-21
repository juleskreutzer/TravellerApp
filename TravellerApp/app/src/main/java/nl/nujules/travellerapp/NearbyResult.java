package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import nl.nujules.travellerapp.util.ActivityType;

public class NearbyResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_result);

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResult.this, Menu.class);
                intent.putExtra("activity", ActivityType.NEARBY);

                startActivity(intent);
            }
        });

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.btnHotelDux1);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnHotelDux2);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btnHotelDux3);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btnHotelDux4);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResult.this, NearbyResultSpecific.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResult.this, NearbyResultSpecific.class);
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResult.this, NearbyResultSpecific.class);
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResult.this, NearbyResultSpecific.class);
                startActivity(intent);
            }
        });
    }
}
