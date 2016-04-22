package nl.nujules.travellerapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.nujules.travellerapp.tasks.getProfileInfoTask;
import nl.nujules.travellerapp.util.ActivityType;

public class Profile extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "http://185.107.212.20:8050";

    SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Replace "Placeholder.this" with the class name you implement this is.
                Intent intent = new Intent(Profile.this, Menu.class);
                intent.putExtra("activity", ActivityType.PROFILE);

                startActivity(intent);
            }
        });

        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Loading");
        dialog.show();

        try {
            fillProfile();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * Show a nice alert when something went wrong.
     * @param message The message that you want to show in the alert.
     */
    public void showError(String message) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(message);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void fillProfile() throws ExecutionException, InterruptedException {

        TextView profileName = (TextView) findViewById(R.id.profileName);
        TextView profileEmail = (TextView) findViewById(R.id.profileEmail);
        TextView profileBio = (TextView) findViewById(R.id.profileBio);
        ImageView profileImage = (ImageView) findViewById(R.id.profileImage);

        Object[] params = new Object[] {Profile.this, SERVER_ADDRESS};
        AsyncTask<Object, Void, Boolean> temp = new getProfileInfoTask().execute(params);
        Boolean status = temp.get();

        profileName.setText(User.getInstance().getUsername());
        profileEmail.setText(User.getInstance().getEmail());
        profileBio.setText(User.getInstance().getBio().equals("") ? "Not specified" : User.getInstance().getBio() );

        if(User.getInstance().getImageBitmap() != null) {
            profileImage.setImageBitmap(User.getInstance().getImageBitmap());
        }

        dialog.hide();

    }
}
