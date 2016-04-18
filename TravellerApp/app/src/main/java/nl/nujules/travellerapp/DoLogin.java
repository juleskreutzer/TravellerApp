package nl.nujules.travellerapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.nujules.travellerapp.util.ActivityType;
import nl.nujules.travellerapp.util.Encrypt;

public class DoLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_login);

        Button login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tempEmail = (EditText) findViewById(R.id.txtEmail);
                EditText tempPassword = (EditText) findViewById(R.id.txtPassword);

                final String email = tempEmail.getText().toString();
                final String password = tempPassword.getText().toString();

                final String encryptedPassword = Encrypt.encryptString(password);


                boolean loginDidSucceed = false;
                try {
                    HttpRequest.Login(email, encryptedPassword);
                    if (User.getInstance().getAuthToken().equals("")) {
                        showError("Email or password incorrect.");
                    } else {
                        loginDidSucceed = true;
                    }
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                if (loginDidSucceed) {
                    // Check if this activity is opened from another activity that we want to return to
                    ActivityType type = (ActivityType) getIntent().getSerializableExtra("activity");
                    Intent intent = null;

                    if (type == null) {
                        intent = new Intent(DoLogin.this, MainActivity.class);
                    } else {
                        switch (type) {
                            case ADD_REVIEW:
                                intent = new Intent(DoLogin.this, AddReview.class);
                                break;
                            default:
                                intent = new Intent(DoLogin.this, MainActivity.class);
                        }
                    }

                    startActivity(intent);
                }
            }
        });

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoLogin.this, Menu.class);
                intent.putExtra("activity", ActivityType.DO_LOGIN);

                startActivity(intent);
            }
        });
    }

    /**
     * Show a nice alert when something went wrong.
     * @param message The message that you want to show in the alert.
     */
    private void showError(String message) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(message);
        dialog.setCancelable(true);
        dialog.show();
    }
}
