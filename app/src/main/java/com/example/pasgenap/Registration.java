package com.example.pasgenap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    EditText txtFullName;
    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    Button btnRegister;
    ProgressBar pbLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Mendapatkan referensi
        txtFullName = findViewById(R.id.txtFullName);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        pbLoadingBar = findViewById(R.id.pbLoadingBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = txtFullName.getText().toString();
                String username = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();


                // untuk set progress bar
                pbLoadingBar.setVisibility(View.VISIBLE);
                btnRegister.setEnabled(false);

                AndroidNetworking.post("https://mediadwi.com/api/latihan/register-user")
                        .addBodyParameter("full_name", fullName)
                        .addBodyParameter("username", username)
                        .addBodyParameter("email", email)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pbLoadingBar.setVisibility(View.GONE);
                                btnRegister.setEnabled(true);

                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status) {
                                        // Registrasi berhasil, lakukan tindakan sesuai kebutuhan
                                        Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(Registration.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(com.androidnetworking.error.ANError error) {
                                pbLoadingBar.setVisibility(View.GONE);
                                btnRegister.setEnabled(true);

                                Toast.makeText(Registration.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}