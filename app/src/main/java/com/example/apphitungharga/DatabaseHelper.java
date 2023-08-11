package com.example.apphitungharga;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_hitungapp.db";
    private static final int DB_VERSION = 1;
    //private static final String PREF_USER_ID = "user_id";
    //private SharedPreferences sharedPreferences;

    Date currentDate = new Date();
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //sharedPreferences = context.getSharedPreferences("user_id", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id interger PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nama text, userid text, j_kelamin text, password text, role text)");
        db.execSQL("CREATE TABLE tabel_hitung(id INTEGER PRIMARY KEY AUTOINCREMENT, nama_umkm text, biayaBakuAwal text,biayaBeliBahan text, biayaTransport text, diskon text, retur text, biayaBakuAkhir text, biayaPekerjaLngsg text, " +
                "biayaPekerjaTdkLngsg text, biayaBhnPenolong text, biayaListrik text, biayaAir text, biayaKomunikasi text, biayaPenyusutan text, biayaLainnya text, biayaProAwal text, biayaProAkhir text, " +
                "biayaJadiAwal text, biayaJadiAkhir text, marginUntung text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int db1, int db2) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS tabel_hitung");
        onCreate(db);
    }

    public Boolean insertData(String nama_umkm, String biayaBakuAwal, String biayaBeliBahan, String biayaTransport ,String diskon,String retur,String biayaBakuAkhir,
                              String biayaPekerjaLngsg,String biayaPekerjaTdkLngsg,String biayaBhnPenolong,String biayaListrik,String biayaAir,
                              String biayaKomunikasi,String biayaPenyusutan,String biayaLainnya,String biayaProAwal,String biayaProAKhir,
                              String biayaJadiAwal,String biayaJadiAkhir, String marginUntung){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Field-field Data
        contentValues.put("nama_umkm",nama_umkm);
        contentValues.put("biayaBakuAwal", biayaBakuAwal);
        contentValues.put("biayaBeliBahan", biayaBeliBahan);
        contentValues.put("biayaTransport", biayaTransport);
        contentValues.put("diskon", diskon);
        contentValues.put("retur", retur);
        contentValues.put("biayaBakuAkhir", biayaBakuAkhir);
        contentValues.put("biayaPekerjaLngsg", biayaPekerjaLngsg);
        contentValues.put("biayaPekerjaTdkLngsg", biayaPekerjaTdkLngsg);
        contentValues.put("biayaBhnPenolong", biayaBhnPenolong);
        contentValues.put("biayaListrik", biayaListrik);
        contentValues.put("biayaAir", biayaAir);
        contentValues.put("biayaKomunikasi", biayaKomunikasi);
        contentValues.put("biayaPenyusutan", biayaPenyusutan);
        contentValues.put("biayaLainnya", biayaLainnya);
        contentValues.put("biayaProAwal", biayaProAwal);
        contentValues.put("biayaProAkhir", biayaProAKhir);
        contentValues.put("biayaJadiAwal", biayaJadiAwal);
        contentValues.put("biayaJadiAkhir", biayaJadiAkhir);
        contentValues.put("marginUntung", marginUntung);
        long insert = db.insert("tabel_hitung", null, contentValues);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<HashMap<String, String>> getAllData(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tabel_hitung",null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama_umkm", cursor.getString(1));
                map.put("biayaBakuAwal", cursor.getString(2));
                map.put("biayaBeliBahan", cursor.getString(3));
                map.put("biayaTransport", cursor.getString(4));
                map.put("diskon", cursor.getString(5));
                map.put("retur", cursor.getString(6));
                map.put("biayaBakuAkhir", cursor.getString(7));
                map.put("biayaPekerjaLngsg", cursor.getString(8));
                map.put("biayaPekerjaTdkLngsg", cursor.getString(9));
                map.put("biayaBhnPenolong", cursor.getString(10));
                map.put("biayaListrik", cursor.getString(11));
                map.put("biayaAir", cursor.getString(12));
                map.put("biayaKomunikasi", cursor.getString(13));
                map.put("biayaPenyusutan", cursor.getString(14));
                map.put("biayaLainnya", cursor.getString(15));
                map.put("biayaProAwal", cursor.getString(16));
                map.put("biayaProAKhir", cursor.getString(17));
                map.put("biayaJadiAwal", cursor.getString(18));
                map.put("biayaJadiAkhir", cursor.getString(19));
                map.put("marginUntung",cursor.getString(20));
                list.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
