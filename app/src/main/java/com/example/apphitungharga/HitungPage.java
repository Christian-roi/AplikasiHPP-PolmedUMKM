package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class HitungPage extends AppCompatActivity {

    DatabaseHelper db;
    Button btnHitung, btnKembali;
    EditText valBakuAwal, valNama, valBakuAkhir, valPekerja, valProAwal, valProAkhir, valJadiAwal
            , valJadiAkhir, valBeliBhn, valTransport, valDiskon, valRetur, valPkrjaTdkLgsg
            , valListrik, valAir, valPenyusut, valLain2, valKom, valPenolong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_page);

        db = new DatabaseHelper(this);

        btnKembali = findViewById(R.id.btnKembali);
        btnHitung = findViewById(R.id.btnHitung);
        valNama = findViewById(R.id.valNama);
        valBakuAwal = findViewById(R.id.valBakuAwal);
        valBeliBhn = findViewById(R.id.valBeliBhn);
        valTransport = findViewById(R.id.valTransport);
        valDiskon = findViewById(R.id.valDiskon);
        valRetur = findViewById(R.id.valRetur);
        valBakuAkhir = findViewById(R.id.valBakuAkhir);
        valPekerja = findViewById(R.id.valPekerja);
        valPkrjaTdkLgsg = findViewById(R.id.valPkrjaTdkLgsg);
        valPenolong = findViewById(R.id.valPenolong);
        valListrik = findViewById(R.id.valListirk);
        valAir = findViewById(R.id.valAir);
        valPenyusut = findViewById(R.id.valPenyusutan);
        valKom = findViewById(R.id.valKom);
        valLain2 = findViewById(R.id.valLain2);
        valProAwal = findViewById(R.id.valProAwal);
        valProAkhir = findViewById(R.id.valProAkhir);
        valJadiAwal =findViewById(R.id.valJadiAwal);
        valJadiAkhir = findViewById(R.id.valJadiAkhir);

        setupEditTextWithThousandSeparator(valBakuAwal);
        setupEditTextWithThousandSeparator(valBeliBhn);
        setupEditTextWithThousandSeparator(valTransport);
        setupEditTextWithThousandSeparator(valDiskon);
        setupEditTextWithThousandSeparator(valRetur);
        setupEditTextWithThousandSeparator(valBakuAkhir);
        setupEditTextWithThousandSeparator(valPekerja);
        setupEditTextWithThousandSeparator(valPkrjaTdkLgsg);
        setupEditTextWithThousandSeparator(valPenolong);
        setupEditTextWithThousandSeparator(valListrik);
        setupEditTextWithThousandSeparator(valAir);
        setupEditTextWithThousandSeparator(valPenyusut);
        setupEditTextWithThousandSeparator(valKom);
        setupEditTextWithThousandSeparator(valLain2);
        setupEditTextWithThousandSeparator(valProAwal);
        setupEditTextWithThousandSeparator(valProAkhir);
        setupEditTextWithThousandSeparator(valJadiAwal);
        setupEditTextWithThousandSeparator(valJadiAkhir);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(HitungPage.this, MainMenu.class);
                startActivity(back);
                finish();
            }
        });

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check = valBakuAwal.getText().toString().isEmpty() || valNama.getText().toString().isEmpty()
                        || valBakuAkhir.getText().toString().isEmpty() || valPekerja.getText().toString().isEmpty()
                        || valBeliBhn.getText().toString().isEmpty() || valTransport.getText().toString().isEmpty()
                        | valDiskon.getText().toString().isEmpty() || valRetur.getText().toString().isEmpty()
                        || valListrik.getText().toString().isEmpty() || valAir.getText().toString().isEmpty()
                        || valKom.getText().toString().isEmpty() || valLain2.getText().toString().isEmpty()
                        || valPkrjaTdkLgsg.getText().toString().isEmpty() || valProAwal.getText().toString().isEmpty()
                        || valProAkhir.getText().toString().isEmpty() || valJadiAwal.getText().toString().isEmpty()
                        || valJadiAkhir.getText().toString().isEmpty() || valPenolong.getText().toString().isEmpty();

                if(check){
                    //double biayaBakuAwal = Double.parseDouble(valBakuAwal.getText().toString().replaceAll(",",""));
                    Toast.makeText(HitungPage.this, "Harap isi seluruh kolom sesuai dengan Nominal atau 0.", Toast.LENGTH_SHORT).show();
                }else{
                    String nama = valNama.getText().toString();
                    String biayaBakuAwal = valBakuAwal.getText().toString();
                    String biayaBeliBahan = valBeliBhn.getText().toString();
                    String biayaTransport = valTransport.getText().toString();
                    String diskon = valDiskon.getText().toString();
                    String retur = valRetur.getText().toString();
                    String biayaBakuAkhir = valBakuAkhir.getText().toString();
                    String biayaPekerja = valPekerja.getText().toString();
                    String biayaPkrjaTdkLgsg = valPkrjaTdkLgsg.getText().toString();
                    String biayaBhnPenolong = valPenolong.getText().toString();
                    String biayaListrik = valListrik.getText().toString();
                    String biayaAir = valAir.getText().toString();
                    String biayaKomunikasi = valKom.getText().toString();
                    String biayaPenyusutan = valPenyusut.getText().toString();
                    String biayaLain2 = valLain2.getText().toString();
                    String biayaProAwal = valProAwal.getText().toString();
                    String biayaProAkhir = valProAkhir.getText().toString();
                    String biayaJadiAwal = valJadiAwal.getText().toString();
                    String biayaJadiAkhir = valJadiAkhir.getText().toString();

                    Boolean insertData = db.insertData(nama, biayaBakuAwal, biayaBeliBahan, biayaTransport, diskon, retur, biayaBakuAkhir, biayaPekerja, biayaPkrjaTdkLgsg, biayaBhnPenolong , biayaListrik, biayaAir, biayaKomunikasi, biayaPenyusutan , biayaLain2, biayaProAwal, biayaProAkhir, biayaJadiAwal, biayaJadiAkhir);
                    if(insertData == true){
                        //Hitung Biaya Pembelian Bahan Baku Bersih
                        double nettoBhnBaku = convertToDouble(biayaBeliBahan) + convertToDouble(biayaTransport) - convertToDouble(diskon) - convertToDouble(retur);
                        //Hitung Biaya Overhead Pabrik
                        double totalBiayaOverheadPabrik = convertToDouble(biayaPkrjaTdkLgsg) + convertToDouble(biayaBhnPenolong)
                                + convertToDouble(biayaListrik) + convertToDouble(biayaAir) + convertToDouble(biayaKomunikasi)
                                + convertToDouble(biayaPenyusutan) + convertToDouble(biayaLain2);
                        //Hitung Biaya Bahan Baku
                        double biayaBahanBaku = convertToDouble(biayaBakuAwal) + nettoBhnBaku - convertToDouble(biayaBakuAkhir);
                        //Hitung Total Biaya Produksi
                        double totalProduksi = biayaBahanBaku + convertToDouble(biayaPekerja) + totalBiayaOverheadPabrik;
                        //Hitung Harga Pokok Produksi
                        double hargaPokokProduksi = totalProduksi + convertToDouble(biayaProAwal) - convertToDouble(biayaProAkhir);
                        //Hitung Harga Pokok Penjualan
                        double hargaPokokPenjualan = hargaPokokProduksi + convertToDouble(biayaJadiAwal) - convertToDouble(biayaJadiAkhir);

                        Intent result  = new Intent(HitungPage.this, ResultPage.class);
                        result.putExtra("namaUmkm", nama);
                        result.putExtra("biayaBakuAwal",biayaBakuAwal);
                        result.putExtra("biayaBeliBhn",biayaBeliBahan);
                        result.putExtra("biayaTransport",biayaTransport);
                        result.putExtra("diskon",diskon);
                        result.putExtra("retur",retur);
                        result.putExtra("biayaBakuAkhir",biayaBakuAkhir);
                        result.putExtra("biayaPekerja",biayaPekerja);
                        result.putExtra("biayaOverheadPabrik",totalBiayaOverheadPabrik);
                        result.putExtra("biayaBahanBaku", biayaBahanBaku);
                        result.putExtra("totalProduksi", totalProduksi);
                        result.putExtra("biayaProAwal",biayaProAwal);
                        result.putExtra("biayaProAkhir",biayaProAkhir);
                        result.putExtra("hargaPokokProduksi", hargaPokokProduksi);
                        result.putExtra("biayaJadiAwal",biayaJadiAwal);
                        result.putExtra("biayaJadiAkhir",biayaJadiAkhir);
                        result.putExtra("hargaPokokPenjualan", hargaPokokPenjualan);
                        startActivity(result);
                        finish();
                    }else {
                        Toast.makeText(HitungPage.this, "Insert ke Database Gagal", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    private void setupEditTextWithThousandSeparator(EditText editText) {
        final DecimalFormat decimalFormat = new DecimalFormat("#,###");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                editText.removeTextChangedListener(this);

                String originalText = editable.toString();

                if (!originalText.isEmpty() && !originalText.equals(",")) {
                    // Hapus pemisah ribuan yang ada sebelumnya (,)
                    String cleanText = originalText.replace(",", "");

                    // Konversi ke bilangan bulat untuk menghilangkan angka desimal
                    long value = Long.parseLong(cleanText);

                    // Format angka dan tambahkan pemisah ribuan
                    String formattedText = decimalFormat.format(value);

                    editText.setText(formattedText);
                    editText.setSelection(formattedText.length());
                }

                editText.addTextChangedListener(this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }

    private double convertToDouble(String stringValue) {
        try {
            return Double.parseDouble(stringValue.replaceAll(",",""));
        } catch (NumberFormatException e) {
            // Jika String tidak dapat diubah menjadi double, tangani sesuai kebutuhan Anda.
            // Misalnya, tampilkan pesan kesalahan atau kembalikan nilai default.
            e.printStackTrace();
            return 0.0; // Nilai default jika konversi gagal
        }
    }
}