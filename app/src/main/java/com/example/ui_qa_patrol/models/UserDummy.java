package com.example.ui_qa_patrol.models;

public class UserDummy {

    private String nik,password,nama;

    public UserDummy(String nik, String password, String nama) {
        this.nik = nik;
        this.password = password;
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
