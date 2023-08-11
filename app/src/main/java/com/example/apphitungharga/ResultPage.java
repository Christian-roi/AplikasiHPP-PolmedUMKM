package com.example.apphitungharga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

public class ResultPage extends AppCompatActivity {
    ActionBar actionBar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView resBahanBaku = findViewById(R.id.resBhnBaku);
        TextView resProduksi = findViewById(R.id.resProduksi);
        TextView resPokPro = findViewById(R.id.resPokPro);
        TextView resHPP = findViewById(R.id.resHPP);
        TextView nama = findViewById(R.id.valNama);
        TextView resHarga = findViewById(R.id.resHarga);
        TextView valMargin = findViewById(R.id.valMargin);
        Button btCetak = findViewById(R.id.btCetak);
        Button btBack = findViewById(R.id.btBack);

        //Ambil nilai dari intent extra
        String namaUmkm = getIntent().getStringExtra("namaUmkm");
        double biayaBahanBaku = getIntent().getDoubleExtra("biayaBahanBaku", 0.0);
        double biayaTotalProduksi = getIntent().getDoubleExtra("totalProduksi", 0.0);
        double hargaPokokProduksi = getIntent().getDoubleExtra("hargaPokokProduksi", 0.0);
        double hargaPokokPenjualan = getIntent().getDoubleExtra("hargaPokokPenjualan", 0.0);
        double hargaPerkiraan = getIntent().getDoubleExtra("hargaPerkiraan",0.0);
        int margin = getIntent().getIntExtra("margin",0);

        //Tampilkan semua nilai
        nama.setText(namaUmkm);
        resBahanBaku.setText(formatUang(biayaBahanBaku));
        resProduksi.setText(formatUang(biayaTotalProduksi));
        resPokPro.setText(formatUang(hargaPokokProduksi));
        resHPP.setText(formatUang(hargaPokokPenjualan));
        resHarga.setText(formatUang(hargaPerkiraan));
        valMargin.setText("Perkiraan Harga (Margin: "+margin+"%)");

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
                Intent back  = new Intent(ResultPage.this, MainMenu.class);
                startActivity(back);
                finish();
            }
        });
    }

    private String formatUang(double nominal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return "Rp. " + decimalFormat.format(nominal) + ".-";
    }

    private void createPDF() throws FileNotFoundException {
        String nama_umkm = getIntent().getStringExtra("namaUmkm");
        double biayaBahanBaku = getIntent().getDoubleExtra("biayaBahanBaku", 0.0);
        double hargaPokokProduksi = getIntent().getDoubleExtra("hargaPokokProduksi", 0.0);
        double hargaPokokPenjualan = getIntent().getDoubleExtra("hargaPokokPenjualan", 0.0);
        double totalBiayaOverheadPabrik = getIntent().getDoubleExtra("biayaOverheadPabrik",0.0);
        String biayaBakuAwal = getIntent().getStringExtra("biayaBakuAwal");
        String biayaBeliBahan = getIntent().getStringExtra("biayaBeliBhn");
        String biayaTransport = getIntent().getStringExtra("biayaTransport");
        String diskon = getIntent().getStringExtra("diskon");
        String retur = getIntent().getStringExtra("retur");
        String biayaBakuAkhir = getIntent().getStringExtra("biayaBakuAkhir");
        String biayaPekerja = getIntent().getStringExtra("biayaPekerja");
        String biayaPkrjaTdklngsg = getIntent().getStringExtra("biayaPkrjaTdklngsg");
        String biayaAir = getIntent().getStringExtra("biayaAir");
        String biayaListrik = getIntent().getStringExtra("biayaListrik");
        String biayaPenyusutan = getIntent().getStringExtra("biayaPenyusutan");
        String biayaBhnPenolong = getIntent().getStringExtra("biayaBhnPenolong");
        String biayaKomunikasi = getIntent().getStringExtra("biayaKomunikasi");
        String biayaLainnya = getIntent().getStringExtra("biayaLainnya");
        double totalProduksi = getIntent().getDoubleExtra("totalProduksi",0.0);
        String biayaProAwal = getIntent().getStringExtra("biayaProAwal");
        String biayaProAkhir = getIntent().getStringExtra("biayaProAkhir");
        String biayaJadiAwal = getIntent().getStringExtra("biayaJadiAwal");
        String biayaJadiAkhir = getIntent().getStringExtra("biayaJadiAkhir");
        String margin = getIntent().getStringExtra("margin");
        double harga = getIntent().getDoubleExtra("hargaPerkiraan",0.0);

        // Lokasi untuk menyimpan file PDF
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String fileName = "HasilHitung_" + timeStamp + ".pdf";
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
            table.addCell(formatUang(Double.parseDouble(biayaPkrjaTdklngsg.replaceAll(",",""))));
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
            table.addCell(formatUang(Double.parseDouble(biayaLainnya.replaceAll(",",""))));
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
            table.addCell(margin+"%");
            table.addCell(createCell("Perkiraan Harga Jual",BaseColor.YELLOW));
            table.addCell(createCell(formatUang(harga),BaseColor.YELLOW));
            document.add(table);
            document.close();
            // Beritahu pengguna bahwa file PDF telah dibuat
            Toast.makeText(this, "File PDF telah dibuat", Toast.LENGTH_SHORT).show();
            //Buka File Pdf
            openPDF(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(color);
        return cell;
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(this, MainMenu.class);
        startActivity(back);
        finish();
    }


}