package nl.nujules.travellerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.nujules.travellerapp.tasks.loginTask;
import nl.nujules.travellerapp.util.ActivityType;
import nl.nujules.travellerapp.util.Encrypt;

public class DoLogin extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "http://185.107.212.20:8050";

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

                String token = null;
                boolean loginDidSucceed = false;


                try {
                    Object[] params = new Object[] {DoLogin.this, SERVER_ADDRESS, email, encryptedPassword};


                    AsyncTask<Object, Void, String> temp = new loginTask().execute(params);
                    token = temp.get();
                    if(token != null) {
                        loginDidSucceed = true;
                        User.getInstance().setAuthToken(token);
                        User.getInstance().setEmail(email);
                    }
                } catch(IllegalArgumentException e) {
                    showError(e.getMessage());

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
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
                            case PROFILE:
                                intent = new Intent(DoLogin.this, Profile.class);
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

        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoLogin.this, DoSignup.class);
                startActivity(intent);
            }
        });
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
}
