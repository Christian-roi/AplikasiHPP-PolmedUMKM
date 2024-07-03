package com.example.apphitungharga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
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
        TextView resHarga = findViewById(R.id.resHarga);
        TextView resHargaPerUnit = findViewById(R.id.resPerUnit);
        TextView valMargin = findViewById(R.id.valMargin);
        TextView valTotalUnit = findViewById(R.id.valTotalUnit);
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
                String marginUntung = cursor.getString(cursor.getColumnIndexOrThrow("marginUntung"));
                String totalUnitProduksi = cursor.getString(cursor.getColumnIndexOrThrow("totalUnitProduksi"));

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
                //Hitung Margin Keuntungan
                double perkiraanHarga = hargaPokokPenjualan / (1 - (convertToDouble(marginUntung) / 100));
                //Hitung Harga Per Unit
                double hargaPerUnit = hargaPokokProduksi / convertToDouble(totalUnitProduksi);
                double harga = Double.parseDouble(formatDoubleWithTwoDecimalPlaces(perkiraanHarga).replaceAll(",",""));

                nama.setText(nama_umkm);
                resBahanBaku.setText(formatUang(biayaBahanBaku));
                resProduksi.setText(formatUang(totalProduksi));
                resPokPro.setText(formatUang(hargaPokokProduksi));
                resHPP.setText(formatUang(hargaPokokPenjualan));
                resHarga.setText(formatUang(harga));
                resHargaPerUnit.setText(formatUang(hargaPerUnit));
                valMargin.setText("Margin Keuntungan ("+marginUntung+"%)");
                valTotalUnit.setText("Harga Pokok Per Unit ("+totalUnitProduksi+" Unit)");

            }while (cursor.moveToNext());
        }

        btCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDownloadFolder();
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

    public static String formatDoubleWithTwoDecimalPlaces(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }

    public int convertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // Handle jika string tidak bisa diubah menjadi integer
            e.printStackTrace();
            return 0; // Nilai default jika konversi gagal
        }
    }

    private String formatUang(double nominal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return "Rp. " + decimalFormat.format(nominal) + ".-";
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
                String marginUntung = cursor.getString(cursor.getColumnIndexOrThrow("marginUntung"));
                String totalUnitProduksi = cursor.getString(cursor.getColumnIndexOrThrow("totalUnitProduksi"));

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
                //Hitung Margin Keuntungan
                double perkiraanHarga = hargaPokokPenjualan / (1 - (convertToDouble(marginUntung) / 100));
                double harga = Double.parseDouble(formatDoubleWithTwoDecimalPlaces(perkiraanHarga).replaceAll(",",""));
                //Hitung Harga Per Unit
                double hargaPerUnit = hargaPokokProduksi / convertToDouble(totalUnitProduksi);

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
                    table.setWidthPercentage(100);
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
                    table.addCell("Diskon");
                    table.addCell(formatUang(Double.parseDouble(diskon.replaceAll(",",""))));
                    table.addCell("Retur");
                    table.addCell(formatUang(Double.parseDouble(retur.replaceAll(",",""))));
                    table.addCell("Biaya Bahan Baku Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaBakuAkhir.replaceAll(",",""))));
                    table.addCell(createCell("Biaya Pemakaian Bahan Baku", BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(biayaBahanBaku), BaseColor.YELLOW));
                    table.addCell("Biaya Pekerja");
                    table.addCell(formatUang(Double.parseDouble(biayaPekerja.replaceAll(",",""))));
                    table.addCell("Biaya Pekerja Tidak Langsung");
                    table.addCell(formatUang(Double.parseDouble(biayaPkrjaTdkLgsg.replaceAll(",",""))));
                    table.addCell("Biaya Listrik");
                    table.addCell(formatUang(Double.parseDouble(biayaListrik.replaceAll(",",""))));
                    table.addCell("Biaya Air");
                    table.addCell(formatUang(Double.parseDouble(biayaAir.replaceAll(",",""))));
                    table.addCell("Biaya Penyusutan");
                    table.addCell(formatUang(Double.parseDouble(biayaPenyusutan.replaceAll(",",""))));
                    table.addCell("Biaya Komunikasi");
                    table.addCell(formatUang(Double.parseDouble(biayaKomunikasi.replaceAll(",",""))));
                    table.addCell("Biaya Bahan Penolong");
                    table.addCell(formatUang(Double.parseDouble(biayaBhnPenolong.replaceAll(",",""))));
                    table.addCell("Biaya Lainnya");
                    table.addCell(formatUang(Double.parseDouble(biayaLain2.replaceAll(",",""))));
                    table.addCell(createCell("Total Biaya Overhead Pabrik", BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(totalBiayaOverheadPabrik), BaseColor.YELLOW));
                    table.addCell(createCell("Total Biaya Produksi",BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(totalProduksi),BaseColor.YELLOW));
                    table.addCell("Persediaan Barang dalam Proses Awal");
                    table.addCell(formatUang(Double.parseDouble(biayaProAwal.replaceAll(",",""))));
                    table.addCell("Persediaan Barang dalam Proses Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaProAkhir.replaceAll(",",""))));
                    table.addCell(createCell("Harga Pokok Produksi",BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(hargaPokokProduksi), BaseColor.YELLOW));
                    table.addCell("Persediaan Barang Jadi Awal");
                    table.addCell(formatUang(Double.parseDouble(biayaJadiAwal.replaceAll(",",""))));
                    table.addCell("Persediaan Barang Jadi Akhir");
                    table.addCell(formatUang(Double.parseDouble(biayaJadiAkhir.replaceAll(",",""))));
                    table.addCell(createCell("Harga Pokok Pejualan", BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(hargaPokokPenjualan), BaseColor.YELLOW));
                    table.addCell("Margin Keuntungan ");
                    table.addCell(marginUntung+"%");
                    table.addCell(createCell("Perkiraan Margin Keuntungan",BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(harga),BaseColor.YELLOW));
                    table.addCell("Total Unit Produksi ");
                    table.addCell(totalUnitProduksi+" Unit");
                    table.addCell(createCell("Harga Pokok Per Unit",BaseColor.YELLOW));
                    table.addCell(createCell(formatUang(hargaPerUnit), BaseColor.YELLOW));
                    document.add(table);
                    document.close();
                    // Beritahu pengguna bahwa file PDF telah dibuat
                    Toast.makeText(this, "File PDF telah dibuat", Toast.LENGTH_SHORT).show();
                    openPDF(file);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
    }

    private static PdfPCell createCell(String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(color);
        return cell;
    }

    private void openPDF(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", pdfFile);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }
}