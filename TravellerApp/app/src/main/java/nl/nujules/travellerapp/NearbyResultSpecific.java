package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.nujules.travellerapp.util.ActivityType;

public class NearbyResultSpecific extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_result_specific);

        Button menuButton = (Button) findViewById(R.id.btnMenu);
        Button btnAddReview = (Button)findViewById(R.id.button);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace "Placeholder.this" with the class name you implement this is.
                Intent intent = new Intent(NearbyResultSpecific.this, Menu.class);
                intent.putExtra("activity", ActivityType.NEARBY);

                startActivity(intent);
            }
        });

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NearbyResultSpecific.this, AddReview.class);
                startActivity(intent);
            }
        });
    }
}
