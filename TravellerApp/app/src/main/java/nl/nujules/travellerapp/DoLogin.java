package nl.nujules.travellerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

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

                boolean loginDidSucceed = false;
                try{
                    loginDidSucceed = HttpRequest.Login(email, password);
                } catch(IllegalArgumentException e) {
                    showError(e.getMessage());
                }

                if(loginDidSucceed) {
                    Intent intent = new Intent(DoLogin.this, MainActivity.class);
                    startActivity(intent);
                }
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
