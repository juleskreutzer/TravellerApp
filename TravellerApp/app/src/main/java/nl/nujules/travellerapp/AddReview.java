package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.net.MalformedURLException;

import nl.nujules.travellerapp.util.ActivityType;

public class AddReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        // Check if user is logged in
        if(User.getInstance().getAuthToken().equals("") || User.getInstance().getAuthToken() == null) {
            // User not logged in
            Intent intent = new Intent(AddReview.this, DoLogin.class);
            intent.putExtra("activity", ActivityType.ADD_REVIEW);
            startActivity(intent);
        }

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddReview.this, Menu.class);
                intent.putExtra("activity", ActivityType.ADD_REVIEW);
                startActivity(intent);
            }
        });

        Spinner keywordsSpinner = (Spinner) findViewById(R.id.addReviewKeywordsSpinnr);
        ArrayAdapter<String> keywordsSpinnerAdapter = null;

        try {
            keywordsSpinnerAdapter = new ArrayAdapter<>(this, R.layout.keywords_item_text, Keywords.getInstance().getKeywords());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        keywordsSpinner.setAdapter(keywordsSpinnerAdapter);
    }
}
