package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    private CardView btnUmkm, btnInfoApp, btnHitungHPP, btnProfil, btnHistory;
    Button btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnUmkm = findViewById(R.id.btnUmkm);
        btnInfoApp = findViewById(R.id.btnInfoApp);
        btnHistory = findViewById(R.id.btnHistory);
        btnHitungHPP = findViewById(R.id.btnHitungHPP);
        btnKeluar = findViewById(R.id.btnKeluar);

        ActivityCompat.requestPermissions(MainMenu.this, new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btnUmkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuInfoUmkm = new Intent(MainMenu.this, CompanyProfile.class);
                startActivity(menuInfoUmkm);
                finish();
            }
        });

        btnInfoApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuInfoApp = new Intent(MainMenu.this, AppInfo.class);
                startActivity(menuInfoApp);
                finish();
            }
        });

        btnHitungHPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuHitung = new Intent(MainMenu.this, HitungPage.class);
                startActivity(menuHitung);
                finish();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuProfil = new Intent(MainMenu.this, HistoryListPage.class);
                startActivity(menuProfil);
                finish();
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent keluar = new Intent(MainMenu.this, LoginPage.class);
//                startActivity(keluar);
//                finish();
                finishAffinity();
                System.exit(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}