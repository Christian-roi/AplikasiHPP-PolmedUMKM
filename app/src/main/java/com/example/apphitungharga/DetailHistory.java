package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public class DetailHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        DatabaseHelper db = new DatabaseHelper(this);
        TextView resBahanBaku = findViewById(R.id.resBhnBaku);
        TextView resProduksi = findViewById(R.id.resProduksi);
        TextView resPokPro = findViewById(R.id.resPokPro);
        TextView resHPP = findViewById(R.id.resHPP);
        TextView nama = findViewById(R.id.valNama);
        Button btCetak = findViewById(R.id.btCetak);
        Button btBack = findViewById(R.id.btBack);

        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tabel_hitung WHERE id = "+idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String nama_umkm = cursor.getString(cursor.getColumnIndexOrThrow("nama_umkm"));
                String biayaBakuAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaBakuAwal"));
                String biayaBeliBahan = cursor.getString(cursor.getColumnIndexOrThrow("biayaBeliBahan"));
                String biayaTransport = cursor.getString(cursor.getColumnIndexOrThrow("biayaTransport"));
                String diskon = cursor.getString(cursor.getColumnIndexOrThrow("diskon"));
                String retur = cursor.getString(cursor.getColumnIndexOrThrow("retur"));
                String biayaBakuAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaBakuAkhir"));
                String biayaPekerja = cursor.getString(cursor.getColumnIndexOrThrow("biayaPekerjaLngsg"));
                String biayaPkrjaTdkLgsg = cursor.getString(cursor.getColumnIndexOrThrow("biayaPekerjaTdkLngsg"));
                String biayaBhnPenolong = cursor.getString(cursor.getColumnIndexOrThrow("biayaBhnPenolong"));
                String biayaListrik = cursor.getString(cursor.getColumnIndexOrThrow("biayaListrik"));
                String biayaAir = cursor.getString(cursor.getColumnIndexOrThrow("biayaAir"));
                String biayaKomunikasi = cursor.getString(cursor.getColumnIndexOrThrow("biayaKomunikasi"));
                String biayaPenyusutan = cursor.getString(cursor.getColumnIndexOrThrow("biayaPenyusutan"));
                String biayaLain2 = cursor.getString(cursor.getColumnIndexOrThrow("biayaLainnya"));
                String biayaProAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaProAwal"));
                String biayaProAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaProAkhir"));
                String biayaJadiAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaJadiAwal"));
                String biayaJadiAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaJadiAkhir"));

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

                nama.setText(nama_umkm);
                resBahanBaku.setText(formatUang(biayaBahanBaku));
                resProduksi.setText(formatUang(totalProduksi));
                resPokPro.setText(formatUang(hargaPokokProduksi));
                resHPP.setText(formatUang(hargaPokokPenjualan));

            }while (cursor.moveToNext());
        }

        btCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPDF();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back  = new Intent(DetailHistory.this, HistoryListPage.class);
                startActivity(back);
                finish();
            }
        });
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

    private String formatUang(double nominal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.-");
        return "Rp. " + decimalFormat.format(nominal);
    }

    private void createPDF() throws FileNotFoundException {
        //Proses hitung
        DatabaseHelper db = new DatabaseHelper(this);
        int idx = getIntent().getIntExtra("id",0);
        String query = "SELECT * FROM tabel_hitung WHERE id = "+idx;
        Cursor cursor = db.getReadableDatabase().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String nama_umkm = cursor.getString(cursor.getColumnIndexOrThrow("nama_umkm"));
                String biayaBakuAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaBakuAwal"));
                String biayaBeliBahan = cursor.getString(cursor.getColumnIndexOrThrow("biayaBeliBahan"));
                String biayaTransport = cursor.getString(cursor.getColumnIndexOrThrow("biayaTransport"));
                String diskon = cursor.getString(cursor.getColumnIndexOrThrow("diskon"));
                String retur = cursor.getString(cursor.getColumnIndexOrThrow("retur"));
                String biayaBakuAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaBakuAkhir"));
                String biayaPekerja = cursor.getString(cursor.getColumnIndexOrThrow("biayaPekerjaLngsg"));
                String biayaPkrjaTdkLgsg = cursor.getString(cursor.getColumnIndexOrThrow("biayaPekerjaTdkLngsg"));
                String biayaBhnPenolong = cursor.getString(cursor.getColumnIndexOrThrow("biayaBhnPenolong"));
                String biayaListrik = cursor.getString(cursor.getColumnIndexOrThrow("biayaListrik"));
                String biayaAir = cursor.getString(cursor.getColumnIndexOrThrow("biayaAir"));
                String biayaKomunikasi = cursor.getString(cursor.getColumnIndexOrThrow("biayaKomunikasi"));
                String biayaPenyusutan = cursor.getString(cursor.getColumnIndexOrThrow("biayaPenyusutan"));
                String biayaLain2 = cursor.getString(cursor.getColumnIndexOrThrow("biayaLainnya"));
                String biayaProAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaProAwal"));
                String biayaProAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaProAkhir"));
                String biayaJadiAwal = cursor.getString(cursor.getColumnIndexOrThrow("biayaJadiAwal"));
                String biayaJadiAkhir = cursor.getString(cursor.getColumnIndexOrThrow("biayaJadiAkhir"));

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

                // Lokasi untuk menyimpan file PDF
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                String fileName = "HPP_"+ nama_umkm + "-" + timeStamp + ".pdf";
                File file = new File(filePath,fileName);
                OutputStream outputStream = new FileOutputStream(file);
                // Inisialisasi dokumen PDF dan penulis PDF
                Document document = new Document(PageSize.A4);
                try {
                    PdfWriter.getInstance(document, outputStream);
                    document.open();
                    Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD);
                    Paragraph title = new Paragraph("Perhitungan Harga Pokok Penjualan\n"+nama_umkm, titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);
                    // Tambahkan tabel untuk data perhitungan
                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(80);
                    table.setSpacingBefore(20f);
                    table.setSpacingAfter(20f);
                    // Tambahkan header tabel
                    Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
                    PdfPCell cell1 = new PdfPCell(new Phrase("Keterangan", headerFont));
                    PdfPCell cell2 = new PdfPCell(new Phrase("Nilai", headerFont));
                    table.addCell(cell1);
                    table.addCell(cell2);
                    //Tabel
                    table.addCell("Biaya Bahan Baku Awal");
                    table.addCell(formatUang(Double.parseDouble(biayaBakuAwal.replaceAll(",",""))));
                    table.addCell("Biaya Pembelian Bahan");
                    table.addCell(formatUang(Double.parseDouble(biayaBeliBahan.replaceAll(",",""))));
                    table.addCell("Biaya Transport");
                    table.addCell(formatUang(Double.parseDouble(biayaTransport.replaceAll(",",""))));
                    table.addCell("Diskom");
                    table.addCell(formatUang(Double.parseDouble(diskon.replaceAll(",",""))));
                    table.addCell("Retur");
                    table.addCell(formatUang(Double.parseDouble(retur.replaceAll(",",""))));
                    table.addCell("Biaya Bahan Baku Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaBakuAkhir.replaceAll(",",""))));
                    table.addCell("Biaya Bahan Baku");
                    table.addCell(formatUang(biayaBahanBaku));
                    table.addCell("Biaya Pekerja");
                    table.addCell(formatUang(Double.parseDouble(biayaPekerja.replaceAll(",",""))));
                    table.addCell("Total Biaya Overhead Pabrik (Air, Listrik, Komunikasi, Dll)");
                    table.addCell(formatUang(totalBiayaOverheadPabrik));
                    table.addCell("Total Biaya Produksi");
                    table.addCell(formatUang(totalProduksi));
                    table.addCell("Persediaan Barang dalam Proses Awal");
                    table.addCell(formatUang(Double.parseDouble(biayaProAwal.replaceAll(",",""))));
                    table.addCell("Persediaan Barang dalam Proses Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaProAkhir.replaceAll(",",""))));
                    table.addCell("Harga Pokok Produksi");
                    table.addCell(formatUang(hargaPokokProduksi));
                    table.addCell("Persediaan Barang Jadi Awal");
                    table.addCell(formatUang(Double.parseDouble(biayaJadiAwal.replaceAll(",",""))));
                    table.addCell("Persediaan Barang Jadi Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaJadiAkhir.replaceAll(",",""))));
                    table.addCell("Harga Pokok Penjualan");
                    table.addCell(formatUang(hargaPokokPenjualan));
                    document.add(table);
                    document.close();
                    // Beritahu pengguna bahwa file PDF telah dibuat
                    Toast.makeText(this, "File PDF telah dibuat", Toast.LENGTH_SHORT).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
    }

    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }
}