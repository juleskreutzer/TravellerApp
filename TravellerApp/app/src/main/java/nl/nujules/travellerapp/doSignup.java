package nl.nujules.travellerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.nujules.travellerapp.tasks.registerTask;
import nl.nujules.travellerapp.util.ActivityType;
import nl.nujules.travellerapp.util.Encrypt;

public class DoSignup extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "http://185.107.212.20:8050";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_signup);

        Button register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tempUsername = (EditText) findViewById(R.id.txtUsername);
                EditText tempEmail = (EditText) findViewById(R.id.txtRegistrationEmail);
                EditText tempPassword = (EditText) findViewById(R.id.txtRegistrationPassword);
                EditText tempPasswordConfirm = (EditText) findViewById(R.id.txtRegistrationPasswordConfirm);

                final String username = tempUsername.getText().toString();
                final String email = tempEmail.getText().toString();
                final String password = tempPassword.getText().toString();
                final String passwordConfirm = tempPasswordConfirm.getText().toString();

                if (username.equals("")) {
                    showError("Username is required.");
                    return;
                }
                if (email.equals("")) {
                    showError("Email is required");
                    return;
                }
                if (password.equals("")) {
                    showError("Password is required");
                    return;
                }
                if (passwordConfirm.equals("")) {
                    showError("Password confirm is required");
                    return;
                }

                if (!password.toLowerCase().equals(passwordConfirm.toLowerCase())) {
                    showError("Passwords don\'t match!");
                    return;
                }

                final String encryptedPassword = Encrypt.encryptString(password);
                Object[] params = new Object[]{DoSignup.this, SERVER_ADDRESS, email, encryptedPassword, username};


                boolean registrationDidSucceed = false;
                try {
                    AsyncTask<Object, Void, Boolean> temp = new registerTask().execute(params);
                    registrationDidSucceed = temp.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                if (registrationDidSucceed) {
                    Intent intent = new Intent(DoSignup.this, DoLogin.class);
                    startActivity(intent);
                }

            }
        });

        Button menuButton = (Button) findViewById(R.id.btnMenu);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoSignup.this, Menu.class);
                intent.putExtra("activity", ActivityType.DO_SIGNUP);

                startActivity(intent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoSignup.this, DoLogin.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Show a nice error message
     * @param message Message you want to display
     */
    public void showError(String message) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(message);
        dialog.setCancelable(true);
        dialog.show();
    }
}
