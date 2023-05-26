package com.example.pasgenap;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar pbLoadingBar;
    TextView btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Mendapatkan referensi
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        pbLoadingBar = findViewById(R.id.pbLoadingBar);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // untuk set progress bar
                pbLoadingBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);

                //hit API
                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                pbLoadingBar.setVisibility(View.GONE);
                                btnLogin.setEnabled(true);

                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status) {
                                        // Contoh menyimpan email ke SharedPreferences
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, username.toString());
                                        editor.putString(PASSWORD_KEY,"");
                                        editor.apply();

                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        // Pindah ke halaman ListActivity
                                        Intent intent = new Intent(MainActivity.this, ListCoktail.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(com.androidnetworking.error.ANError error) {
                                pbLoadingBar.setVisibility(View.GONE);
                                btnLogin.setEnabled(true);

                                Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(view);
            }
        });
    }

    public void registerClicked(View view) {
        // Pindah ke halaman Register Page
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
    }
}
