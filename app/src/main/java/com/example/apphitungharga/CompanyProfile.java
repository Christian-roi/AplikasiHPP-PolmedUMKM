package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CompanyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        ImageView img = findViewById(R.id.banner);
        img.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }
}