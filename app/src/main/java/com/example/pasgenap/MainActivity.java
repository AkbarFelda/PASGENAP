package com.example.pasgenap;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar pbLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        pbLoadingBar = findViewById(R.id.pbLoadingBar);

        // Mendapatkan referensi ke tombol "Sign in with Google"
        Button btnSignInGoogle = findViewById(R.id.btnSignInGoogle);

        // Mendapatkan gambar logo Google
        Drawable googleLogo = getResources().getDrawable(R.drawable.google_logo);
        googleLogo.setBounds(0, 0, googleLogo.getIntrinsicWidth(), googleLogo.getIntrinsicHeight());

        // Membuat teks dengan ikon gambar
        SpannableString spannableString = new SpannableString("Sign in with Google");
        ImageSpan imageSpan = new ImageSpan(googleLogo, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        // Mengatur teks dengan ikon gambar ke tombol
        btnSignInGoogle.setText(spannableString);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                pbLoadingBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);

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
                                        editor.putString(EMAIL_KEY, username);
                                        editor.putString(PASSWORD_KEY, password);
                                        editor.apply();

                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
    }
}



