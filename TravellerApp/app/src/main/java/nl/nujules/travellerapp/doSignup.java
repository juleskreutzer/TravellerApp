package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nl.nujules.travellerapp.util.Encrypt;

public class DoSignup extends AppCompatActivity {

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

                if(username.equals("") || username == null) { showError("Username is required."); return; }
                if(email.equals("") || email == null) { showError("Email is required"); return; }
                if(password.equals("") || password == null) { showError("Password is required"); return; }
                if(passwordConfirm.equals("") || passwordConfirm == null) { showError("Password confirm is required"); return; }

                if(!password.toLowerCase().equals(passwordConfirm.toLowerCase())) { showError("Passwords don\'t match!"); return; }

                final String encryptedPassword = Encrypt.encryptString(password);

                boolean registrationDidSucceed = false;
                try{
                    registrationDidSucceed = HttpRequest.Register(username, email, encryptedPassword);
                } catch(IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                if(registrationDidSucceed) {
                    Intent intent = new Intent(DoSignup.this, DoLogin.class);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * Show a nice error message
     * @param message Message you want to display
     */
    private void showError(String message) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(message);
        dialog.setCancelable(true);
        dialog.show();
    }
}
