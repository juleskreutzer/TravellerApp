package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Placeholder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace "Placeholder.this" with the class name you implement this is.
                Intent intent = new Intent(Placeholder.this, Menu.class);
                intent.putExtra("activity", MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
