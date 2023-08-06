package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apphitungharga.adapter.HitungAdapter;
import com.example.apphitungharga.model.HitungModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryListPage extends AppCompatActivity {

    ListView listView;
    List<HitungModel> lists = new ArrayList<>();
    HitungAdapter adapter;
    DatabaseHelper db;
    private FloatingActionButton kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.list_item);
        kembali = findViewById(R.id.fab);
        adapter = new HitungAdapter(HistoryListPage.this, lists);
        listView.setAdapter(adapter);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String idx = lists.get(position).getId();
                Intent intent = new Intent(HistoryListPage.this, DetailHistory.class);
                intent.putExtra("id",Integer.parseInt(idx));
                startActivity(intent);
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMenu = new Intent(HistoryListPage.this, MainMenu.class);
                startActivity(backToMenu);
                finish();
            }
        });
    }

    private void getData(){
        ArrayList<HashMap<String, String>> rows = db.getAllData();
        for (int i = 0; i<rows.size(); i++){
            String id = rows.get(i).get("id");
            String nama_umkm = rows.get(i).get("nama_umkm");
            HitungModel data = new HitungModel();
            data.setId(id);
            data.setNama_umkm(nama_umkm);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
        lists.clear();
        getData();
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }
}