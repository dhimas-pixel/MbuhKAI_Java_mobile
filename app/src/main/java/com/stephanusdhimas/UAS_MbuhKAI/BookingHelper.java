package com.stephanusdhimas.UAS_MbuhKAI;

public class BookingHelper {
    String nama, asal, tujuan, tanggal, dewasa, anak, total, waktu, key;

    public BookingHelper() {
    }

    public BookingHelper(String nama, String asal, String tujuan, String tanggal, String waktu, String dewasa, String anak, String total) {
        this.nama = nama;
        this.asal = asal;
        this.tujuan = tujuan;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.dewasa = dewasa;
        this.anak = anak;
        this.total = total;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDewasa() {
        return dewasa;
    }

    public void setDewasa(String dewasa) {
        this.dewasa = dewasa;
    }

    public String getAnak() {
        return anak;
    }

    public void setAnak(String anak) {
        this.anak = anak;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
