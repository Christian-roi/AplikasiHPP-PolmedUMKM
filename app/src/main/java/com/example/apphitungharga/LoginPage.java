package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button btLogin  = findViewById(R.id.btLogin);
        Button btRegist = findViewById(R.id.btRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginPage.this, MainMenu.class);
                startActivity(login);
                finish();
            }
        });

        btRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(regist);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}