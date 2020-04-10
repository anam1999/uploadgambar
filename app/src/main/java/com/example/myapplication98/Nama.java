package com.example.myapplication98;

import android.os.Parcel;
import android.os.Parcelable;

public class Nama implements Parcelable {
    private int id;
    private String nama,gambar;

    public Nama(int id, String nama , String gambar) {
        this.id = id;
        this.gambar = gambar;
        this.nama = nama;

    }

    protected Nama(Parcel in) {
        id = in.readInt();
        nama = in.readString();
        gambar = in.readString();
    }

    public static final Creator<Nama> CREATOR = new Creator<Nama>() {
        @Override
        public Nama createFromParcel(Parcel in) {
            return new Nama(in);
        }

        @Override
        public Nama[] newArray(int size) {
            return new Nama[size];
        }
    };

    public String getGambar() {
        return gambar;
    }
    public String getNama() {
        return nama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nama);
        dest.writeString(gambar);
    }
}
