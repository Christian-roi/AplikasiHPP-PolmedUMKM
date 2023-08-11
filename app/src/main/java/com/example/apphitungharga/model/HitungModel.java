package com.example.apphitungharga.model;

public class HitungModel {
    private String  id, nama_umkm,  biayaBakuAwal,  biayaBeliBahan,  biayaTransport , diskon, retur, biayaBakuAkhir,
            biayaPekerjaLngsg, biayaPekerjaTdkLngsg, biayaBhnPenolong, biayaListrik, biayaAir,
            biayaKomunikasi, biayaPenyusutan, biayaLainnya, biayaProAwal, biayaProAKhir, biayaJadiAwal, biayaJadiAkhir, marginUntung;

    public HitungModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_umkm() {
        return nama_umkm;
    }

    public void setNama_umkm(String nama_umkm) {
        this.nama_umkm = nama_umkm;
    }

    public String getBiayaBakuAwal() {
        return biayaBakuAwal;
    }

    public void setBiayaBakuAwal(String biayaBakuAwal) {
        this.biayaBakuAwal = biayaBakuAwal;
    }

    public String getBiayaBeliBahan() {
        return biayaBeliBahan;
    }

    public void setBiayaBeliBahan(String biayaBeliBahan) {
        this.biayaBeliBahan = biayaBeliBahan;
    }

    public String getBiayaTransport() {
        return biayaTransport;
    }

    public void setBiayaTransport(String biayaTransport) {
        this.biayaTransport = biayaTransport;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getRetur() {
        return retur;
    }

    public void setRetur(String retur) {
        this.retur = retur;
    }

    public String getBiayaBakuAkhir() {
        return biayaBakuAkhir;
    }

    public void setBiayaBakuAkhir(String biayaBakuAkhir) {
        this.biayaBakuAkhir = biayaBakuAkhir;
    }

    public String getBiayaPekerjaLngsg() {
        return biayaPekerjaLngsg;
    }

    public void setBiayaPekerjaLngsg(String biayaPekerjaLngsg) {
        this.biayaPekerjaLngsg = biayaPekerjaLngsg;
    }

    public String getBiayaPekerjaTdkLngsg() {
        return biayaPekerjaTdkLngsg;
    }

    public void setBiayaPekerjaTdkLngsg(String biayaPekerjaTdkLngsg) {
        this.biayaPekerjaTdkLngsg = biayaPekerjaTdkLngsg;
    }

    public String getBiayaBhnPenolong() {
        return biayaBhnPenolong;
    }

    public void setBiayaBhnPenolong(String biayaBhnPenolong) {
        this.biayaBhnPenolong = biayaBhnPenolong;
    }

    public String getBiayaListrik() {
        return biayaListrik;
    }

    public void setBiayaListrik(String biayaListrik) {
        this.biayaListrik = biayaListrik;
    }

    public String getBiayaAir() {
        return biayaAir;
    }

    public void setBiayaAir(String biayaAir) {
        this.biayaAir = biayaAir;
    }

    public String getBiayaKomunikasi() {
        return biayaKomunikasi;
    }

    public void setBiayaKomunikasi(String biayaKomunikasi) {
        this.biayaKomunikasi = biayaKomunikasi;
    }

    public String getBiayaPenyusutan() {
        return biayaPenyusutan;
    }

    public void setBiayaPenyusutan(String biayaPenyusutan) {
        this.biayaPenyusutan = biayaPenyusutan;
    }

    public String getBiayaLainnya() {
        return biayaLainnya;
    }

    public void setBiayaLainnya(String biayaLainnya) {
        this.biayaLainnya = biayaLainnya;
    }

    public String getBiayaProAwal() {
        return biayaProAwal;
    }

    public void setBiayaProAwal(String biayaProAwal) {
        this.biayaProAwal = biayaProAwal;
    }

    public String getBiayaProAKhir() {
        return biayaProAKhir;
    }

    public void setBiayaProAKhir(String biayaProAKhir) {
        this.biayaProAKhir = biayaProAKhir;
    }

    public String getBiayaJadiAwal() {
        return biayaJadiAwal;
    }

    public void setBiayaJadiAwal(String biayaJadiAwal) {
        this.biayaJadiAwal = biayaJadiAwal;
    }

    public String getBiayaJadiAkhir() {
        return biayaJadiAkhir;
    }

    public void setBiayaJadiAkhir(String biayaJadiAkhir) {
        this.biayaJadiAkhir = biayaJadiAkhir;
    }

    public String getMarginUntung() {
        return marginUntung;
    }

    public void setMarginUntung(String marginUntung) {
        this.marginUntung = marginUntung;
    }

    public HitungModel(String id, String nama_umkm, String biayaBakuAwal, String biayaBeliBahan, String biayaTransport , String diskon, String retur, String biayaBakuAkhir,
                       String biayaPekerjaLngsg, String biayaPekerjaTdkLngsg, String biayaBhnPenolong, String biayaListrik, String biayaAir,
                       String biayaKomunikasi, String biayaPenyusutan, String biayaLainnya, String biayaProAwal, String biayaProAKhir, String biayaJadiAwal,
                       String biayaJadiAkhir, String marginUntung){
        this.id = id;
        this.nama_umkm = nama_umkm;
        this.biayaBakuAwal = biayaBakuAwal;
        this.biayaBeliBahan = biayaBeliBahan;
        this.biayaTransport = biayaTransport;
        this.diskon = diskon;
        this.retur = retur;
        this.biayaBakuAkhir = biayaBakuAkhir;
        this.biayaPekerjaLngsg = biayaPekerjaLngsg;
        this.biayaPekerjaTdkLngsg = biayaPekerjaTdkLngsg;
        this.biayaBhnPenolong = biayaBhnPenolong;
        this.biayaListrik = biayaListrik;
        this.biayaAir = biayaAir;
        this.biayaKomunikasi = biayaKomunikasi;
        this.biayaPenyusutan = biayaPenyusutan;
        this.biayaLainnya = biayaLainnya;
        this.biayaProAwal = biayaProAwal;
        this.biayaProAKhir = biayaProAKhir;
        this.biayaJadiAwal = biayaJadiAwal;
        this.biayaJadiAkhir = biayaJadiAkhir;
        this.marginUntung = marginUntung;
    }
}
